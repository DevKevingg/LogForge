package codes.kevinhenriquez.logforge.error.suggestion;

import codes.kevinhenriquez.logforge.error.CodeFrame;
import codes.kevinhenriquez.logforge.error.ErrorHint;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * © 2026 LogForge. All rights reserved.
 *
 * File              : SmartSuggestionEngine.java
 * Author            : Kevin Henriquez
 * Creation Date     : 16/05/2026
 *
 * Legal Notice:
 * Unauthorized copying, distribution, or modification of this
 * source code is strictly prohibited without explicit permission
 * from the author.
 * =============================================================================
 */
public class SmartSuggestionEngine {
    private static final Pattern SIMPLE_METHOD_CALL = Pattern.compile("^\\s*([A-Za-z_$][\\w$]*)\\.[^;]+;\\s*$");
    private static final Pattern CHAINED_METHOD_CALL = Pattern.compile("^\\s*([A-Za-z_$][\\w$]*)\\.([A-Za-z_$][\\w$]*\\([^)]*\\))\\.[^;]+;\\s*$");
    private static final Pattern LIST_ACCESS = Pattern.compile("([A-Za-z_$][\\w$]*)\\.get\\(([^)]+)\\)");
    private static final Pattern ARRAY_ACCESS = Pattern.compile("([A-Za-z_$][\\w$]*)\\[([^]]+)]");
    private static final Pattern NUMBER_PARSE = Pattern.compile("(Integer\\.parseInt|Long\\.parseLong|Double\\.parseDouble)\\(([^)]+)\\)");
    private static final Pattern DIVISION = Pattern.compile("([A-Za-z_$][\\w$]*)\\s*/\\s*([A-Za-z_$][\\w$]*)");
    private static final Pattern CAST_ASSIGNMENT = Pattern.compile("^\\s*([A-Za-z_$][\\w$]*)\\s+([A-Za-z_$][\\w$]*)\\s*=\\s*\\(([^)]+)\\)\\s*([A-Za-z_$][\\w$]*);\\s*$");

    public String improve(Throwable throwable, CodeFrame codeFrame, ErrorHint baseHint) {
        if (throwable == null || codeFrame == null || !codeFrame.available()) {
            return baseHint.exampleFix();
        }

        String line = codeFrame.currentLine().strip();
        if (line.isBlank() || line.contains("=")) {
            return baseHint.exampleFix();
        }

        Optional<String> smartFix = Optional.empty();
        if (throwable instanceof NullPointerException) {
            smartFix = nullPointerFix(line);
        } else if (throwable instanceof IndexOutOfBoundsException) {
            smartFix = indexOutOfBoundsFix(line);
        } else if (throwable instanceof NumberFormatException) {
            smartFix = numberFormatFix(line);
        } else if (throwable instanceof ArithmeticException) {
            smartFix = arithmeticFix(line);
        } else if (throwable instanceof ClassCastException) {
            smartFix = classCastFix(line);
        }

        return smartFix.orElse(baseHint.exampleFix());
    }

    private Optional<String> nullPointerFix(String line) {
        Matcher chainedMatcher = CHAINED_METHOD_CALL.matcher(line);
        if (chainedMatcher.matches()) {
            String root = chainedMatcher.group(1);
            String nestedCall = chainedMatcher.group(2);
            return Optional.of("""
                    if (%s != null && %s.%s != null) {
                        %s
                    }
                    """.formatted(root, root, nestedCall, line));
        }

        Matcher simpleMatcher = SIMPLE_METHOD_CALL.matcher(line);
        if (simpleMatcher.matches()) {
            String variable = simpleMatcher.group(1);
            return Optional.of("""
                    if (%s != null) {
                        %s
                    }
                    """.formatted(variable, line));
        }
        return Optional.empty();
    }

    private Optional<String> indexOutOfBoundsFix(String line) {
        Matcher listMatcher = LIST_ACCESS.matcher(line);
        if (listMatcher.find()) {
            String list = listMatcher.group(1);
            String index = listMatcher.group(2).strip();
            return Optional.of("""
                    if (%s >= 0 && %s < %s.size()) {
                        %s
                    }
                    """.formatted(index, index, list, line));
        }

        Matcher arrayMatcher = ARRAY_ACCESS.matcher(line);
        if (arrayMatcher.find()) {
            String array = arrayMatcher.group(1);
            String index = arrayMatcher.group(2).strip();
            return Optional.of("""
                    if (%s >= 0 && %s < %s.length) {
                        %s
                    }
                    """.formatted(index, index, array, line));
        }
        return Optional.empty();
    }

    private Optional<String> numberFormatFix(String line) {
        return NUMBER_PARSE.matcher(line).find()
                ? Optional.of("""
                try {
                    %s
                } catch (NumberFormatException exception) {
                    // handle invalid number
                }
                """.formatted(line))
                : Optional.empty();
    }

    private Optional<String> arithmeticFix(String line) {
        Matcher matcher = DIVISION.matcher(line);
        if (!matcher.find()) {
            return Optional.empty();
        }
        String divisor = matcher.group(2);
        return Optional.of("""
                if (%s != 0) {
                    int result = %s;
                }
                """.formatted(divisor, line.replace(";", "")));
    }

    private Optional<String> classCastFix(String line) {
        Matcher matcher = CAST_ASSIGNMENT.matcher(line);
        if (!matcher.matches()) {
            return Optional.empty();
        }
        String declaredType = matcher.group(1);
        String variable = matcher.group(2);
        String castType = matcher.group(3).strip();
        String object = matcher.group(4);
        if (!declaredType.equals(castType)) {
            return Optional.empty();
        }
        return Optional.of("""
                if (%s instanceof %s %s) {
                    // use %s
                }
                """.formatted(object, castType, variable, variable));
    }
}
