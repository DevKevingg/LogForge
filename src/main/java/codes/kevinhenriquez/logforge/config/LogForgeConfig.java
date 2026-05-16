package codes.kevinhenriquez.logforge.config;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
 * © 2026 ComandaGo. All rights reserved.
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
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class LogForgeConfig {
    @Getter
    @Setter
    private static boolean colorsEnabled = true;

    @Getter
    @Setter
    private static boolean timestampEnabled = true;

    @Getter
    @Setter
    private static boolean iconsEnabled = true;

    @Getter
    private static int levelPadding = 7;

    public static void setLevelPadding(int levelPadding) {
        if (levelPadding < 0) {
            throw new IllegalArgumentException("Level padding is negative");
        }

        LogForgeConfig.levelPadding = Math.min(levelPadding, 20);
    }

    public static void reset() {
        colorsEnabled = true;
        timestampEnabled = true;
        iconsEnabled = true;
        levelPadding = 7;
    }
}
