package codes.kevinhenriquez.logforge.config;

import codes.kevinhenriquez.logforge.enums.LogLevelEnum;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Properties;

/*
 * © 2026 LogForge. All rights reserved.
 *
 * File              : LogForgeConfig.java
 * Author            : Kevin Henriquez
 * Creation Date     : 16/05/2026
 *
 * Legal Notice:
 * Unauthorized copying, distribution, or modification of this
 * source code is strictly prohibited without explicit permission
 * from the author.
 * =============================================================================
 */
public final class LogForgeConfig {
    private static boolean colorsEnabled = true;

    private static boolean timestampEnabled = true;

    private static boolean iconsEnabled = true;

    private static int levelPadding = 7;

    private static LogLevelEnum minimumLevel = LogLevelEnum.DEBUG;

    private static boolean enabled = true;

    private static boolean compactMode = false;

    private static boolean unicodeEnabled = true;

    private static String timestampPattern = "HH:mm:ss";

    private static DateTimeFormatter timestampFormatter = DateTimeFormatter.ofPattern(timestampPattern);

    private static Theme theme = Theme.DEFAULT;

    private static Path externalConfigPath;

    private LogForgeConfig() {
    }

    public static boolean isColorsEnabled() {
        return colorsEnabled;
    }

    public static void setColorsEnabled(boolean colorsEnabled) {
        LogForgeConfig.colorsEnabled = colorsEnabled;
    }

    public static boolean isTimestampEnabled() {
        return timestampEnabled;
    }

    public static void setTimestampEnabled(boolean timestampEnabled) {
        LogForgeConfig.timestampEnabled = timestampEnabled;
    }

    public static boolean isIconsEnabled() {
        return iconsEnabled;
    }

    public static void setIconsEnabled(boolean iconsEnabled) {
        LogForgeConfig.iconsEnabled = iconsEnabled;
    }

    public static int getLevelPadding() {
        return levelPadding;
    }

    public static LogLevelEnum getMinimumLevel() {
        return minimumLevel;
    }

    public static boolean isEnabled() {
        return enabled;
    }

    public static void setEnabled(boolean enabled) {
        LogForgeConfig.enabled = enabled;
    }

    public static boolean isCompactMode() {
        return compactMode;
    }

    public static void setCompactMode(boolean compactMode) {
        LogForgeConfig.compactMode = compactMode;
    }

    public static boolean isUnicodeEnabled() {
        return unicodeEnabled;
    }

    public static void setUnicodeEnabled(boolean unicodeEnabled) {
        LogForgeConfig.unicodeEnabled = unicodeEnabled;
    }

    public static String getTimestampPattern() {
        return timestampPattern;
    }

    public static DateTimeFormatter getTimestampFormatter() {
        return timestampFormatter;
    }

    public static void setTimestampPattern(String timestampPattern) {
        if (timestampPattern == null || timestampPattern.isBlank()) {
            throw new IllegalArgumentException("Timestamp pattern is blank");
        }

        LogForgeConfig.timestampFormatter = DateTimeFormatter.ofPattern(timestampPattern);
        LogForgeConfig.timestampPattern = timestampPattern;
    }

    public static Theme getTheme() {
        return theme;
    }

    public static void setTheme(Theme theme) {
        if (theme == null) {
            throw new IllegalArgumentException("Theme is null");
        }

        LogForgeConfig.theme = theme;
    }

    public static void setLevelPadding(int levelPadding) {
        if (levelPadding < 0) {
            throw new IllegalArgumentException("Level padding is negative");
        }

        LogForgeConfig.levelPadding = Math.min(levelPadding, 20);
    }

    public static void setMinimumLevel(LogLevelEnum minimumLevel) {
        if (minimumLevel == null) {
            throw new IllegalArgumentException("Minimum level is null");
        }

        LogForgeConfig.minimumLevel = minimumLevel;
    }

    public static void reset() {
        colorsEnabled = true;
        timestampEnabled = true;
        iconsEnabled = true;
        levelPadding = 7;
        minimumLevel = LogLevelEnum.DEBUG;
        enabled = true;
        compactMode = false;
        unicodeEnabled = true;
        timestampPattern = "HH:mm:ss";
        timestampFormatter = DateTimeFormatter.ofPattern(timestampPattern);
        theme = Theme.DEFAULT;
        externalConfigPath = null;
    }

    public static void loadFromEnvironment() {
        apply("LOGFORGE_COLORS_ENABLED", System.getenv("LOGFORGE_COLORS_ENABLED"));
        apply("LOGFORGE_TIMESTAMP_ENABLED", System.getenv("LOGFORGE_TIMESTAMP_ENABLED"));
        apply("LOGFORGE_ICONS_ENABLED", System.getenv("LOGFORGE_ICONS_ENABLED"));
        apply("LOGFORGE_LEVEL_PADDING", System.getenv("LOGFORGE_LEVEL_PADDING"));
        apply("LOGFORGE_MINIMUM_LEVEL", System.getenv("LOGFORGE_MINIMUM_LEVEL"));
        apply("LOGFORGE_ENABLED", System.getenv("LOGFORGE_ENABLED"));
        apply("LOGFORGE_COMPACT_MODE", System.getenv("LOGFORGE_COMPACT_MODE"));
        apply("LOGFORGE_UNICODE_ENABLED", System.getenv("LOGFORGE_UNICODE_ENABLED"));
        apply("LOGFORGE_TIMESTAMP_PATTERN", System.getenv("LOGFORGE_TIMESTAMP_PATTERN"));
        apply("LOGFORGE_THEME", System.getenv("LOGFORGE_THEME"));
    }

    public static void loadFromFile(Path path) {
        if (path == null) {
            throw new IllegalArgumentException("Config path is null");
        }

        Properties properties = new Properties();
        try (var reader = Files.newBufferedReader(path)) {
            properties.load(reader);
        } catch (IOException exception) {
            throw new IllegalArgumentException("Unable to load config file: " + path, exception);
        }

        properties.forEach((key, value) -> apply(String.valueOf(key), String.valueOf(value)));
        externalConfigPath = path;
    }

    public static void reload() {
        loadFromEnvironment();
        if (externalConfigPath != null) {
            loadFromFile(externalConfigPath);
        }
    }

    private static void apply(String key, String value) {
        if (value == null || value.isBlank()) {
            return;
        }

        switch (key.toUpperCase(Locale.ROOT)) {
            case "LOGFORGE_COLORS_ENABLED", "COLORS_ENABLED" -> setColorsEnabled(Boolean.parseBoolean(value));
            case "LOGFORGE_TIMESTAMP_ENABLED", "TIMESTAMP_ENABLED" -> setTimestampEnabled(Boolean.parseBoolean(value));
            case "LOGFORGE_ICONS_ENABLED", "ICONS_ENABLED" -> setIconsEnabled(Boolean.parseBoolean(value));
            case "LOGFORGE_LEVEL_PADDING", "LEVEL_PADDING" -> setLevelPadding(Integer.parseInt(value));
            case "LOGFORGE_MINIMUM_LEVEL", "MINIMUM_LEVEL" -> setMinimumLevel(LogLevelEnum.valueOf(value.toUpperCase(Locale.ROOT)));
            case "LOGFORGE_ENABLED", "ENABLED" -> setEnabled(Boolean.parseBoolean(value));
            case "LOGFORGE_COMPACT_MODE", "COMPACT_MODE" -> setCompactMode(Boolean.parseBoolean(value));
            case "LOGFORGE_UNICODE_ENABLED", "UNICODE_ENABLED" -> setUnicodeEnabled(Boolean.parseBoolean(value));
            case "LOGFORGE_TIMESTAMP_PATTERN", "TIMESTAMP_PATTERN" -> setTimestampPattern(value);
            case "LOGFORGE_THEME", "THEME" -> setTheme(Theme.valueOf(value.toUpperCase(Locale.ROOT)));
            default -> {
                // Ignore unknown properties to keep config forward-compatible.
            }
        }
    }
}
