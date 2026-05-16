package codes.kevinhenriquez.logforge;

import codes.kevinhenriquez.logforge.enums.LogLevelEnum;
import codes.kevinhenriquez.logforge.format.LogFormatter;
import codes.kevinhenriquez.logforge.format.MessageFormatter;
import codes.kevinhenriquez.logforge.output.ConsoleWriter;
import codes.kevinhenriquez.logforge.task.LogTask;

/*
 * © 2026 ComandaGo. All rights reserved.
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
        log(LogLevelEnum.INFO, MessageFormatter.format(message, args));
    }

    public static void success(String message, Object... args) {
        log(LogLevelEnum.SUCCESS, MessageFormatter.format(message, args));
    }

    public static void warning(String message, Object... args) {
        log(LogLevelEnum.WARNING, MessageFormatter.format(message, args));
    }

    public static void error(String message, Object... args) {
        String formatted = FORMATTER.format(
                LogLevelEnum.ERROR,
                MessageFormatter.format(message, args));

        WRITER.errorLog(formatted);
    }

    public static void error(String message, Throwable throwable) {
        String formatted = FORMATTER.format(
                LogLevelEnum.ERROR,
                MessageFormatter.format(message));

        WRITER.errorLog(formatted);

        if (throwable != null) {
            throwable.printStackTrace(System.err);
        }
    }

    public static void error(String message, Throwable throwable, Object... args) {
        String formatted = FORMATTER.format(
                LogLevelEnum.ERROR,
                MessageFormatter.format(message, args));

        WRITER.errorLog(formatted);

        if (throwable != null) {
            throwable.printStackTrace(System.err);
        }
    }

    public static void error(Throwable throwable, String message, Object... args) {
        String formatted = FORMATTER.format(
                LogLevelEnum.ERROR,
                MessageFormatter.format(message, args));

        WRITER.errorLog(formatted);

        if (throwable != null) {
            throwable.printStackTrace(System.err);
        }
    }

    public static void debug(String message, Object... args) {
        String formatted = FORMATTER.format(
                LogLevelEnum.DEBUG,
                MessageFormatter.format(message, args));

        WRITER.debugLog(formatted);
    }

    private static void log(LogLevelEnum level, String message, Object... args) {
        String formatted = FORMATTER.format(
                level,
                MessageFormatter.format(message, args));

        WRITER.writeLog(formatted);
    }

    public static void api(String method, String path, int status, long durationMs) {
        log(LogLevelEnum.API, "{} {} {} {}ms", method, path, status, durationMs);
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
}
