package codes.kevinhenriquez.logforge;

import codes.kevinhenriquez.logforge.config.LogForgeConfig;
import codes.kevinhenriquez.logforge.enums.LogLevelEnum;
import codes.kevinhenriquez.logforge.format.LogFormatter;
import codes.kevinhenriquez.logforge.format.MessageFormatter;
import codes.kevinhenriquez.logforge.output.ConsoleWriter;
import codes.kevinhenriquez.logforge.task.LogTask;
import codes.kevinhenriquez.logforge.utils.AnsiColor.*;

import static codes.kevinhenriquez.logforge.utils.AnsiColor.*;

/*
 * © 2026 LogForge. All rights reserved.
 *
 * File              : LogForge.java
 * Author            : Kevin Henriquez
 * Creation Date     : 16/05/2026
 *
 * Legal Notice:
 * Unauthorized copying, distribution, or modification of this
 * source code is strictly prohibited without explicit permission
 * from the author.
 * =============================================================================
 */
public class LogForge {
    private static final ConsoleWriter WRITER = new ConsoleWriter();
    private static final LogFormatter FORMATTER = new LogFormatter();

    public static void info(String message, Object... args) {
        if (!isLevelEnabled(LogLevelEnum.INFO)) {
            return;
        }

        log(LogLevelEnum.INFO, MessageFormatter.format(message, args));
    }

    public static void success(String message, Object... args) {
        if (!isLevelEnabled(LogLevelEnum.SUCCESS)) {
            return;
        }

        log(LogLevelEnum.SUCCESS, MessageFormatter.format(message, args));
    }

    public static void warning(String message, Object... args) {
        if (!isLevelEnabled(LogLevelEnum.WARNING)) {
            return;
        }

        log(LogLevelEnum.WARNING, MessageFormatter.format(message, args));
    }

    public static void error(String message, Object... args) {
        if (!isLevelEnabled(LogLevelEnum.ERROR)) {
            return;
        }

        String formatted = FORMATTER.format(
                LogLevelEnum.ERROR,
                MessageFormatter.format(message, args));

        WRITER.errorLog(formatted);
    }

    public static void error(String message, Throwable throwable) {
        if (!isLevelEnabled(LogLevelEnum.ERROR)) {
            return;
        }

        String formatted = FORMATTER.format(
                LogLevelEnum.ERROR,
                MessageFormatter.format(message));

        WRITER.errorLog(formatted);

        if (throwable != null) {
            throwable.printStackTrace(System.err);
        }
    }

    public static void error(String message, Throwable throwable, Object... args) {
        if (!isLevelEnabled(LogLevelEnum.ERROR)) {
            return;
        }

        String formatted = FORMATTER.format(
                LogLevelEnum.ERROR,
                MessageFormatter.format(message, args));

        WRITER.errorLog(formatted);

        if (throwable != null) {
            throwable.printStackTrace(System.err);
        }
    }

    public static void error(Throwable throwable, String message, Object... args) {
        if (!isLevelEnabled(LogLevelEnum.ERROR)) {
            return;
        }

        String formatted = FORMATTER.format(
                LogLevelEnum.ERROR,
                MessageFormatter.format(message, args));

        WRITER.errorLog(formatted);

        if (throwable != null) {
            throwable.printStackTrace(System.err);
        }
    }

    public static void debug(String message, Object... args) {
        if (!isLevelEnabled(LogLevelEnum.DEBUG)) {
            return;
        }

        String formatted = FORMATTER.format(
                LogLevelEnum.DEBUG,
                MessageFormatter.format(message, args));

        WRITER.debugLog(formatted);
    }

    private static void log(LogLevelEnum level, String message, Object... args) {
        if (!isLevelEnabled(level)) {
            return;
        }

        String formatted = FORMATTER.format(
                level,
                MessageFormatter.format(message, args));

        WRITER.writeLog(formatted);
    }

    public static void api(String method, String path, int status, long durationMs) {
        if (!isLevelEnabled(LogLevelEnum.API)) {
            return;
        }

        String statusColor = status >= 500
                ? BRIGHT_RED
                : status >= 400
                ? BRIGHT_YELLOW
                : BRIGHT_GREEN;

        String message = BOLD + method + RESET
                + " " + path
                + " " + statusColor + status + RESET
                + " " + GRAY + durationMs + "ms" + RESET;

        log(LogLevelEnum.API, message);
    }

    public static void time(String label, LogTask task) {
        long start = System.currentTimeMillis();

        try {
            task.run();

            long duration  = System.currentTimeMillis() - start;
            success("{} completed in {}ms", label, duration);
        } catch (Exception e) {
            long duration  = System.currentTimeMillis() - start;
            error("Error while running {} after {}ms", e, label, duration);
        }
    }

    private static boolean isLevelEnabled(LogLevelEnum level) {
        return level.getPriority() >= LogForgeConfig.getMinimumLevel().getPriority();
    }
}
