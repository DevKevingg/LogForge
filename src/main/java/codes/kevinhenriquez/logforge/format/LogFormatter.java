package codes.kevinhenriquez.logforge.format;

import codes.kevinhenriquez.logforge.enums.LogLevelEnum;
import codes.kevinhenriquez.logforge.utils.AnsiColor;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/*
 * © 2026 ComandaGo. All rights reserved.
 *
 * File              : LogFormatter.java
 * Author            : Kevin Henriquez
 * Creation Date     : 16/05/2026
 *
 * Legal Notice:
 * Unauthorized copying, distribution, or modification of this
 * source code is strictly prohibited without explicit permission
 * from the author.
 * =============================================================================
 */
public class LogFormatter {
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

    public String format(LogLevelEnum level, String message) {
        String time = LocalTime.now().format(TIME_FORMATTER);

        return color(level)
                + icon(level)
                + " "
                + padding(level.name())
                + AnsiColor.RESET
                + " "
                + AnsiColor.GRAY
                + time
                +  AnsiColor.RESET
                + " "
                + message;
    }

    private String icon(LogLevelEnum level) {
        return switch (level) {
            case INFO -> "ℹ";
            case SUCCESS -> "✓";
            case WARNING -> "⚠";
            case ERROR -> "✕";
            case DEBUG -> "•";
            case API -> "→";
        };
    }

    private String color(LogLevelEnum level) {
        return switch (level) {
            case INFO -> AnsiColor.BLUE;
            case SUCCESS -> AnsiColor.GREEN;
            case WARNING -> AnsiColor.YELLOW;
            case ERROR -> AnsiColor.RED;
            case DEBUG -> AnsiColor.GRAY;
            case API -> AnsiColor.CYAN;
        };
    }

    private String padding(String value) {
        return String.format("%-7s", value);
    }
}
