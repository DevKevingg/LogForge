package codes.kevinhenriquez.logforge;

import codes.kevinhenriquez.logforge.config.LogForgeConfig;
import codes.kevinhenriquez.logforge.enums.LogLevelEnum;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/*
 * © 2026 LogForge. All rights reserved.
 *
 * File              : LogForgeConfigTest.java
 * Author            : Kevin Henriquez
 * Creation Date     : 16/05/2026
 *
 * Legal Notice:
 * Unauthorized copying, distribution, or modification of this
 * source code is strictly prohibited without explicit permission
 * from the author.
 * =============================================================================
 */
class LogForgeConfigTest {
    @AfterEach
    void resetConfig() {
        LogForgeConfig.reset();
    }

    @Test
    void shouldDisableColors() {
        LogForgeConfig.setColorsEnabled(false);

        assertFalse(LogForgeConfig.isColorsEnabled());
    }

    @Test
    void shouldDisableIcons() {
        LogForgeConfig.setIconsEnabled(false);

        assertFalse(LogForgeConfig.isIconsEnabled());
    }

    @Test
    void shouldDisableTimestamp() {
        LogForgeConfig.setTimestampEnabled(false);

        assertFalse(LogForgeConfig.isTimestampEnabled());
    }

    @Test
    void shouldSetLevelPadding() {
        LogForgeConfig.setLevelPadding(12);

        assertEquals(12, LogForgeConfig.getLevelPadding());
    }

    @Test
    void shouldLimitLevelPaddingToTwenty() {
        LogForgeConfig.setLevelPadding(50);

        assertEquals(20, LogForgeConfig.getLevelPadding());
    }

    @Test
    void shouldRejectNegativeLevelPadding() {
        assertThrows(
                IllegalArgumentException.class,
                () -> LogForgeConfig.setLevelPadding(-1)
        );
    }

    @Test
    void shouldSetMinimumLevel() {
        LogForgeConfig.setMinimumLevel(LogLevelEnum.WARNING);

        assertEquals(LogLevelEnum.WARNING, LogForgeConfig.getMinimumLevel());
    }

    @Test
    void shouldRejectNullMinimumLevel() {
        assertThrows(
                IllegalArgumentException.class,
                () -> LogForgeConfig.setMinimumLevel(null)
        );
    }
}
