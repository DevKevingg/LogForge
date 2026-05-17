package codes.kevinhenriquez.logforge.error;

/*
 * © 2026 LogForge. All rights reserved.
 *
 * File              : CodeFrame.java
 * Author            : Kevin Henriquez
 * Creation Date     : 16/05/2026
 *
 * Legal Notice:
 * Unauthorized copying, distribution, or modification of this
 * source code is strictly prohibited without explicit permission
 * from the author.
 * =============================================================================
 */
public record CodeFrame(
        boolean available,
        int lineNumber,
        String previousLine,
        String currentLine,
        String nextLine) {

    public static CodeFrame unavailable() {
        return new CodeFrame(false, -1, "", "", "");
    }

    public String format() {
        if (!available) {
            return "";
        }

        int previousLineNumber = lineNumber - 1;
        int nextLineNumber = lineNumber + 1;
        int width = String.valueOf(nextLineNumber).length();

        StringBuilder builder = new StringBuilder("Code:");

        if (!previousLine.isEmpty()) {
            builder.append(System.lineSeparator())
                    .append(formatLine(previousLineNumber, previousLine, width));
        }

        builder.append(System.lineSeparator())
                .append(formatLine(lineNumber, currentLine, width))
                .append(System.lineSeparator())
                .append(" ".repeat(width))
                .append(" | ^ failing line");

        if (!nextLine.isEmpty()) {
            builder.append(System.lineSeparator())
                    .append(formatLine(nextLineNumber, nextLine, width));
        }

        return builder.toString();
    }

    private String formatLine(int number, String line, int width) {
        return String.format("%" + width + "d | %s", number, line);
    }
}
