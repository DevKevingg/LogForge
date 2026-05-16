package codes.kevinhenriquez.logforge.table;

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
        int[] widths = calculateWidths(headers, rows);

        StringBuilder builder = new StringBuilder();

        builder.append(border("┌", "┬", "┐", widths));
        builder.append(header(headers, widths));
        builder.append(border("├", "┼", "┤", widths));

        for (String[] row : rows) {
            builder.append(line(row, widths));
        }

        builder.append(border("└", "┴", "┘", widths));

        return builder.toString();
    }

    private int[] calculateWidths(String[] headers, String[][] rows) {
        int[] widths = new int[headers.length];

        for (int i = 0; i < headers.length; i++) {
            widths[i] = headers[i].length();
        }

        for (String[] row : rows) {
            for (int i = 0; i < row.length; i++) {
                widths[i] = Math.max(widths[i], row[i].length());
            }
        }

        return widths;
    }

    private String border(String left, String middle, String right, int[] widths) {
        StringBuilder builder = new StringBuilder();

        builder.append(left);

        for (int i = 0; i < widths.length; i++) {
            builder.append("─".repeat(widths[i] + 2));

            if (i < widths.length - 1) {
                builder.append(middle);
            }
        }

        builder.append(right).append("\n");

        return builder.toString();
    }

    private String header(String[] headers, int[] widths) {
        return line(headers, widths);
    }

    private String line(String[] values, int[] widths) {
        StringBuilder builder = new StringBuilder();

        builder.append("│");

        for (int i = 0; i < values.length; i++) {
            builder.append(" ")
                    .append(pad(values[i], widths[i]))
                    .append(" │");
        }

        builder.append("\n");

        return builder.toString();
    }

    private String pad(String value, int width) {
        return String.format("%-" + width + "s", value);
    }
}
