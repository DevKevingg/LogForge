package codes.kevinhenriquez.logforge.config;

import codes.kevinhenriquez.logforge.enums.LogLevelEnum;

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
    }
}
