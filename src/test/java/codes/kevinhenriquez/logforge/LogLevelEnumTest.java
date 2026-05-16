package codes.kevinhenriquez.logforge;

import codes.kevinhenriquez.logforge.enums.LogLevelEnum;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/*
 * © 2026 LogForge. All rights reserved.
 *
 * File              : LogLevelEnumTest.java
 * Author            : Kevin Henriquez
 * Creation Date     : 16/05/2026
 *
 * Legal Notice:
 * Unauthorized copying, distribution, or modification of this
 * source code is strictly prohibited without explicit permission
 * from the author.
 * =============================================================================
 */
public class LogLevelEnumTest {
    @Test
    void errorShouldHaveHigherPriorityThanWarning() {
        assertTrue(LogLevelEnum.ERROR.getPriority() > LogLevelEnum.WARNING.getPriority());
    }

    @Test
    void warningShouldHaveHigherPriorityThanInfo() {
        assertTrue(LogLevelEnum.WARNING.getPriority() > LogLevelEnum.INFO.getPriority());
    }

    @Test
    void infoShouldHaveHigherPriorityThanDebug() {
        assertTrue(LogLevelEnum.INFO.getPriority() > LogLevelEnum.DEBUG.getPriority());
    }
}
