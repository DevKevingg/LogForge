package codes.kevinhenriquez.logforge.table;

import codes.kevinhenriquez.logforge.config.LogForgeConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/*
 * © 2026 LogForge. All rights reserved.
 *
 * File              : LogTableFormatterTest.java
 * Author            : Kevin Henriquez
 * Creation Date     : 17/05/2026
 *
 * Legal Notice:
 * Unauthorized copying, distribution, or modification of this
 * source code is strictly prohibited without explicit permission
 * from the author.
 * =============================================================================
 */
class LogTableFormatterTest {
    @AfterEach
    void resetConfig() {
        LogForgeConfig.reset();
    }

    @Test
    void shouldRenderAsciiTableWhenUnicodeIsDisabled() {
        LogForgeConfig.setUnicodeEnabled(false);

        String table = new LogTableFormatter().format(
                new String[]{"ID", "User"},
                new String[][]{{"1", "Kevin"}}
        );

        assertTrue(table.startsWith("+----+-------+"));
        assertTrue(table.contains("| 1  | Kevin |"));
    }
}
