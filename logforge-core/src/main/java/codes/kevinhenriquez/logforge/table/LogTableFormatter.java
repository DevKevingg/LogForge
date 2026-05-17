package codes.kevinhenriquez.logforge.table;

import codes.kevinhenriquez.logforge.config.LogForgeConfig;

/*
 * © 2026 LogForge. All rights reserved.
 *
 * File              : LogTableFormatter.java
 * Author            : Kevin Henriquez
 * Creation Date     : 16/05/2026
 *
 * Legal Notice:
 * Unauthorized copying, distribution, or modification of this
 * source code is strictly prohibited without explicit permission
 * from the author.
 * =============================================================================
 */
public class LogTableFormatter {

    public String format(String[] headers, String[][] rows) {
        String[] safeHeaders = headers == null ? new String[0] : headers;
        String[][] safeRows = rows == null ? new String[0][] : rows;
        int[] widths = calculateWidths(safeHeaders, safeRows);

        StringBuilder builder = new StringBuilder();

        builder.append(border(topLeft(), topJoin(), topRight(), widths));
        builder.append(line(safeHeaders, widths));
        builder.append(border(middleLeft(), middleJoin(), middleRight(), widths));

        for (String[] row : safeRows) {
            builder.append(line(row == null ? new String[0] : row, widths));
        }

        builder.append(border(bottomLeft(), bottomJoin(), bottomRight(), widths));

        return builder.toString();
    }

    private int[] calculateWidths(String[] headers, String[][] rows) {
        int[] widths = new int[headers.length];

        for (int i = 0; i < headers.length; i++) {
            widths[i] = safe(headers[i]).length();
        }

        for (String[] row : rows) {
            if (row == null) {
                continue;
            }

            for (int i = 0; i < Math.min(row.length, widths.length); i++) {
                widths[i] = Math.max(widths[i], safe(row[i]).length());
            }
        }

        return widths;
    }

    private String border(String left, String middle, String right, int[] widths) {
        StringBuilder builder = new StringBuilder(left);

        for (int i = 0; i < widths.length; i++) {
            builder.append(horizontal().repeat(widths[i] + 2));

            if (i < widths.length - 1) {
                builder.append(middle);
            }
        }

        return builder.append(right).append("\n").toString();
    }

    private String line(String[] values, int[] widths) {
        StringBuilder builder = new StringBuilder(vertical());

        for (int i = 0; i < widths.length; i++) {
            String value = i < values.length ? safe(values[i]) : "";
            builder.append(" ")
                    .append(pad(value, widths[i]))
                    .append(" ")
                    .append(vertical());
        }

        return builder.append("\n").toString();
    }

    private String pad(String value, int width) {
        return String.format("%-" + width + "s", value);
    }

    private String safe(String value) {
        return value == null ? "" : value;
    }

    private String horizontal() { return LogForgeConfig.isUnicodeEnabled() ? "─" : "-"; }
    private String vertical() { return LogForgeConfig.isUnicodeEnabled() ? "│" : "|"; }
    private String topLeft() { return LogForgeConfig.isUnicodeEnabled() ? "┌" : "+"; }
    private String topJoin() { return LogForgeConfig.isUnicodeEnabled() ? "┬" : "+"; }
    private String topRight() { return LogForgeConfig.isUnicodeEnabled() ? "┐" : "+"; }
    private String middleLeft() { return LogForgeConfig.isUnicodeEnabled() ? "├" : "+"; }
    private String middleJoin() { return LogForgeConfig.isUnicodeEnabled() ? "┼" : "+"; }
    private String middleRight() { return LogForgeConfig.isUnicodeEnabled() ? "┤" : "+"; }
    private String bottomLeft() { return LogForgeConfig.isUnicodeEnabled() ? "└" : "+"; }
    private String bottomJoin() { return LogForgeConfig.isUnicodeEnabled() ? "┴" : "+"; }
    private String bottomRight() { return LogForgeConfig.isUnicodeEnabled() ? "┘" : "+"; }
}
