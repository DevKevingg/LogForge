package codes.kevinhenriquez.logforge.error;

import codes.kevinhenriquez.logforge.error.suggestion.SmartSuggestionEngine;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/*
 * © 2026 LogForge. All rights reserved.
 *
 * File              : ErrorAnalyzer.java
 * Author            : Kevin Henriquez
 * Creation Date     : 16/05/2026
 *
 * Legal Notice:
 * Unauthorized copying, distribution, or modification of this
 * source code is strictly prohibited without explicit permission
 * from the author.
 * =============================================================================
 */
public class ErrorAnalyzer {
    private final SourceCodeReader sourceCodeReader;
    private final SmartSuggestionEngine smartSuggestionEngine;

    public ErrorAnalyzer() {
        this(new SourceCodeReader(), new SmartSuggestionEngine());
    }

    ErrorAnalyzer(SourceCodeReader sourceCodeReader) {
        this(sourceCodeReader, new SmartSuggestionEngine());
    }

    ErrorAnalyzer(SourceCodeReader sourceCodeReader, SmartSuggestionEngine smartSuggestionEngine) {
        this.sourceCodeReader = sourceCodeReader;
        this.smartSuggestionEngine = smartSuggestionEngine;
    }

    public ErrorHint analyze(Throwable throwable) {
        Throwable root = rootCauseOf(throwable);
        StackTraceElement locationElement = locationElementOf(root);
        String location = locationOf(locationElement);
        CodeFrame codeFrame = codeFrameOf(locationElement);
        ErrorHint baseHint = baseHintFor(root, location, codeFrame);
        String improvedFix = smartSuggestionEngine.improve(root, codeFrame, baseHint);

        return new ErrorHint(
                baseHint.errorName(),
                baseHint.location(),
                baseHint.reason(),
                baseHint.suggestion(),
                improvedFix,
                baseHint.codeFrame()
        );
    }

    private ErrorHint baseHintFor(Throwable throwable, String location, CodeFrame codeFrame) {
        if (throwable == null) {
            return new ErrorHint("Unknown error", location, "An unexpected error happened.", "Review the exception type, message and stacktrace.", "Check the failing line and handle the exceptional case.", codeFrame);
        }

        if (throwable instanceof NullPointerException) {
            return new ErrorHint("NullPointerException", location, "A value is null and Java cannot call methods or access fields on it.", "Check the object before using it, initialize it, or validate the input before calling methods.", "if (value != null) {\n    value.toString();\n}\n", codeFrame);
        }
        if (throwable instanceof IndexOutOfBoundsException) {
            return new ErrorHint("IndexOutOfBoundsException", location, "The code is trying to access a position that does not exist in a list, array or string.", "Check the size before accessing the index.", "if (index >= 0 && index < list.size()) {\n    list.get(index);\n}\n", codeFrame);
        }
        if (throwable instanceof NumberFormatException) {
            return new ErrorHint("NumberFormatException", location, "The code is trying to convert text into a number, but the text is not a valid numeric value.", "Validate the input before parsing it.", "try {\n    Integer.parseInt(value);\n} catch (NumberFormatException exception) {\n    // handle invalid number\n}\n", codeFrame);
        }
        if (throwable instanceof ClassCastException) {
            return new ErrorHint("ClassCastException", location, "The code is trying to cast an object to a type that it does not actually belong to.", "Use instanceof before casting.", "if (object instanceof User user) {\n    // use user\n}\n", codeFrame);
        }
        if (throwable instanceof IllegalArgumentException) {
            return new ErrorHint("IllegalArgumentException", location, "A method received an argument that is not valid for its expected rules.", "Validate arguments before calling the method or improve the method validation message.", "if (value < 0) {\n    throw new IllegalArgumentException(\"value cannot be negative\");\n}\n", codeFrame);
        }
        if (throwable instanceof IllegalStateException) {
            return new ErrorHint("IllegalStateException", location, "The object is being used while it is in a state that does not allow this operation.", "Check the lifecycle or state transition before calling the method.", "if (service.isReady()) {\n    service.run();\n}\n", codeFrame);
        }
        if (throwable instanceof UnsupportedOperationException) {
            return new ErrorHint("UnsupportedOperationException", location, "The requested operation is not supported by the current object implementation.", "Use a mutable implementation or choose an operation supported by this type.", "List<String> values = new ArrayList<>(List.of(\"a\"));\nvalues.add(\"b\");\n", codeFrame);
        }
        if (throwable instanceof ArithmeticException) {
            return new ErrorHint("ArithmeticException", location, "An invalid arithmetic operation happened, commonly division by zero.", "Check the divisor before dividing.", "if (divisor != 0) {\n    int result = number / divisor;\n}\n", codeFrame);
        }
        if (throwable instanceof FileNotFoundException) {
            return new ErrorHint("FileNotFoundException", location, "Java could not find or open the requested file.", "Check the file path, permissions and whether the file exists.", "Path path = Path.of(\"file.txt\");\nif (Files.exists(path)) {\n    // read file\n}\n", codeFrame);
        }
        if (throwable instanceof IOException) {
            return new ErrorHint("IOException", location, "An input/output operation failed while reading or writing data.", "Check the path, permissions, network resource or storage availability.", "try {\n    Files.readString(path);\n} catch (IOException exception) {\n    // handle I/O failure\n}\n", codeFrame);
        }
        if (throwable instanceof SecurityException) {
            return new ErrorHint("SecurityException", location, "The runtime denied an operation because the current code lacks permission.", "Check permissions, security policy and access rights before retrying.", "if (Files.isReadable(path)) {\n    Files.readString(path);\n}\n", codeFrame);
        }
        if (throwable instanceof RuntimeException) {
            return new ErrorHint("RuntimeException", location, "An unexpected runtime error happened while the program was running.", "Check the exception message and the stacktrace location.", "Review the failing line and validate the values used there.", codeFrame);
        }

        return new ErrorHint(throwable.getClass().getSimpleName(), location, "An unexpected error happened.", "Review the exception type, message and stacktrace.", "Check the failing line and handle the exceptional case.", codeFrame);
    }

    private Throwable rootCauseOf(Throwable throwable) {
        Throwable current = throwable;
        while (current != null && current.getCause() != null && current.getCause() != current) {
            current = current.getCause();
        }
        return current;
    }

    private StackTraceElement locationElementOf(Throwable throwable) {
        if (throwable == null || throwable.getStackTrace().length == 0) {
            return null;
        }

        List<StackTraceElement> elements = Arrays.asList(throwable.getStackTrace());
        return elements.stream()
                .filter(element -> element.getClassName().startsWith("codes.kevinhenriquez"))
                .findFirst()
                .orElse(elements.get(0));
    }

    private String locationOf(StackTraceElement element) {
        if (element == null || element.getFileName() == null || element.getLineNumber() < 0) {
            return "Unknown location";
        }
        return element.getFileName() + ":" + element.getLineNumber();
    }

    private CodeFrame codeFrameOf(StackTraceElement element) {
        if (element == null) {
            return CodeFrame.unavailable();
        }
        return sourceCodeReader.read(element.getFileName(), element.getLineNumber());
    }
}
