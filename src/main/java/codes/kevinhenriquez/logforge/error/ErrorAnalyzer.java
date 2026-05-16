package codes.kevinhenriquez.logforge.error;

import codes.kevinhenriquez.logforge.error.suggestion.SmartSuggestionEngine;

import java.io.FileNotFoundException;

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
        String location = locationOf(throwable);
        CodeFrame codeFrame = codeFrameOf(throwable);
        ErrorHint baseHint = baseHintFor(throwable, location, codeFrame);
        String improvedFix = smartSuggestionEngine.improve(throwable, codeFrame, baseHint);

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
            return new ErrorHint(
                    "Unknown error",
                    location,
                    "An unexpected error happened.",
                    "Review the exception type, message and stacktrace.",
                    "Check the failing line and handle the exceptional case.",
                    codeFrame
            );
        }

        if (throwable instanceof NullPointerException) {
            return new ErrorHint(
                    "NullPointerException",
                    location,
                    "A value is null and Java cannot call methods or access fields on it.",
                    "Check the object before using it, initialize it, or validate the input before calling methods.",
                    """
                    if (value != null) {
                        value.toString();
                    }
                    """,
                    codeFrame
            );
        }

        if (throwable instanceof IndexOutOfBoundsException) {
            return new ErrorHint(
                    "IndexOutOfBoundsException",
                    location,
                    "The code is trying to access a position that does not exist in a list, array or string.",
                    "Check the size before accessing the index.",
                    """
                    if (index >= 0 && index < list.size()) {
                        list.get(index);
                    }
                    """,
                    codeFrame
            );
        }

        if (throwable instanceof NumberFormatException) {
            return new ErrorHint(
                    "NumberFormatException",
                    location,
                    "The code is trying to convert text into a number, but the text is not a valid numeric value.",
                    "Validate the input before parsing it.",
                    """
                    try {
                        Integer.parseInt(value);
                    } catch (NumberFormatException exception) {
                        // handle invalid number
                    }
                    """,
                    codeFrame
            );
        }

        if (throwable instanceof ClassCastException) {
            return new ErrorHint(
                    "ClassCastException",
                    location,
                    "The code is trying to cast an object to a type that it does not actually belong to.",
                    "Use instanceof before casting.",
                    """
                    if (object instanceof User user) {
                        // use user
                    }
                    """,
                    codeFrame
            );
        }

        if (throwable instanceof IllegalArgumentException) {
            return new ErrorHint(
                    "IllegalArgumentException",
                    location,
                    "A method received an argument that is not valid for its expected rules.",
                    "Validate arguments before calling the method or improve the method validation message.",
                    """
                    if (value < 0) {
                        throw new IllegalArgumentException("value cannot be negative");
                    }
                    """,
                    codeFrame
            );
        }

        if (throwable instanceof ArithmeticException) {
            return new ErrorHint(
                    "ArithmeticException",
                    location,
                    "An invalid arithmetic operation happened, commonly division by zero.",
                    "Check the divisor before dividing.",
                    """
                    if (divisor != 0) {
                        int result = number / divisor;
                    }
                    """,
                    codeFrame
            );
        }

        if (throwable instanceof FileNotFoundException) {
            return new ErrorHint(
                    "FileNotFoundException",
                    location,
                    "Java could not find or open the requested file.",
                    "Check the file path, permissions and whether the file exists.",
                    """
                    Path path = Path.of("file.txt");
                    if (Files.exists(path)) {
                        // read file
                    }
                    """,
                    codeFrame
            );
        }

        if (throwable instanceof RuntimeException) {
            return new ErrorHint(
                    "RuntimeException",
                    location,
                    "An unexpected runtime error happened while the program was running.",
                    "Check the exception message and the stacktrace location.",
                    "Review the failing line and validate the values used there.",
                    codeFrame
            );
        }

        return new ErrorHint(
                throwable.getClass().getSimpleName(),
                location,
                "An unexpected error happened.",
                "Review the exception type, message and stacktrace.",
                "Check the failing line and handle the exceptional case.",
                codeFrame
        );
    }

    private String locationOf(Throwable throwable) {
        if (throwable == null || throwable.getStackTrace().length == 0) {
            return "Unknown location";
        }

        StackTraceElement element = throwable.getStackTrace()[0];
        if (element.getFileName() == null || element.getLineNumber() < 0) {
            return "Unknown location";
        }

        return element.getFileName() + ":" + element.getLineNumber();
    }

    private CodeFrame codeFrameOf(Throwable throwable) {
        if (throwable == null || throwable.getStackTrace().length == 0) {
            return CodeFrame.unavailable();
        }

        StackTraceElement element = throwable.getStackTrace()[0];
        return sourceCodeReader.read(element.getFileName(), element.getLineNumber());
    }
}
