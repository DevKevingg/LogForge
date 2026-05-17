package codes.kevinhenriquez.logforge;

import codes.kevinhenriquez.logforge.format.MessageFormatter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/*
 * © 2026 ComandaGo. All rights reserved.
 *
 * File              : MessageFormatterTest.java
 * Author            : Kevin Henriquez
 * Creation Date     : 16/05/2026
 *
 * Legal Notice:
 * Unauthorized copying, distribution, or modification of this
 * source code is strictly prohibited without explicit permission
 * from the author.
 * =============================================================================
 */
class MessageFormatterTest {

    @Test
    void shouldReplacePlaceholders() {
        String result = MessageFormatter.format(
                "User {} accessed {}",
                "kevin",
                "/admin"
        );

        assertEquals("User kevin accessed /admin", result);
    }

    @Test
    void shouldKeepExtraPlaceholdersWhenNoArgsAvailable() {
        String result = MessageFormatter.format(
                "Hello {} {}",
                "Kevin"
        );

        assertEquals("Hello Kevin {}", result);
    }

    @Test
    void shouldReturnOriginalMessageWhenNoArgs() {
        String result = MessageFormatter.format("Application started");

        assertEquals("Application started", result);
    }
}
