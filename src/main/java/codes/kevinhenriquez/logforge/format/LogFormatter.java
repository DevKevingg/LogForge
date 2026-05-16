package codes.kevinhenriquez.logforge.format;

import codes.kevinhenriquez.logforge.config.LogForgeConfig;
import codes.kevinhenriquez.logforge.enums.LogLevelEnum;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static codes.kevinhenriquez.logforge.utils.AnsiColor.*;

/*
 * © 2026 LogForge. All rights reserved.
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

        String icon = LogForgeConfig.isIconsEnabled()
                ? icon(level) + " "
                : "";

        String levelText = padding(level.name());

        String timestamp = LogForgeConfig.isTimestampEnabled()
                ? " " + GRAY + time + RESET
                : "";

        return applyColor(level, icon + BOLD + levelText + RESET)
                + timestamp
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
            case INFO -> BRIGHT_BLUE;
            case SUCCESS -> BRIGHT_GREEN;
            case WARNING -> BRIGHT_YELLOW;
            case ERROR -> BRIGHT_RED;
            case DEBUG -> GRAY;
            case API -> BRIGHT_CYAN;
        };
    }

    private String applyColor(LogLevelEnum level, String value) {
        if (!LogForgeConfig.isColorsEnabled()) {
            return stripAnsi(value);
        }

        return color(level) + value + RESET;
    }

    private String stripAnsi(String value) {
        return value.replace("\\u001B\\[[;\\d]*m", "");
    }

    private String padding(String value) {
        return String.format("%-" + LogForgeConfig.getLevelPadding() + "s", value);
    }
}
