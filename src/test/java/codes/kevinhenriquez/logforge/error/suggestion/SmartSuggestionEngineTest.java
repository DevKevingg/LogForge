package codes.kevinhenriquez.logforge.error.suggestion;

import codes.kevinhenriquez.logforge.error.CodeFrame;
import codes.kevinhenriquez.logforge.error.ErrorHint;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/*
 * © 2026 LogForge. All rights reserved.
 *
 * File              : SmartSuggestionEngineTest.java
 * Author            : Kevin Henriquez
 * Creation Date     : 16/05/2026
 *
 * Legal Notice:
 * Unauthorized copying, distribution, or modification of this
 * source code is strictly prohibited without explicit permission
 * from the author.
 * =============================================================================
 */
class SmartSuggestionEngineTest {
    private final SmartSuggestionEngine engine = new SmartSuggestionEngine();

    @Test
    void shouldImproveSimpleNullPointerSuggestion() {
        String fix = engine.improve(
                new NullPointerException(),
                frame("value.toLowerCase();"),
                baseHint("fallback")
        );

        assertEquals("""
                if (value != null) {
                    value.toLowerCase();
                }
                """, fix);
    }

    @Test
    void shouldImproveChainedNullPointerSuggestion() {
        String fix = engine.improve(
                new NullPointerException(),
                frame("user.getName().toLowerCase();"),
                baseHint("fallback")
        );

        assertEquals("""
                if (user != null && user.getName() != null) {
                    user.getName().toLowerCase();
                }
                """, fix);
    }

    @Test
    void shouldImproveListIndexSuggestion() {
        String fix = engine.improve(
                new IndexOutOfBoundsException(),
                frame("list.get(index);"),
                baseHint("fallback")
        );

        assertEquals("""
                if (index >= 0 && index < list.size()) {
                    list.get(index);
                }
                """, fix);
    }

    @Test
    void shouldImproveNumberFormatSuggestion() {
        String fix = engine.improve(
                new NumberFormatException(),
                frame("Integer.parseInt(value);"),
                baseHint("fallback")
        );

        assertEquals("""
                try {
                    Integer.parseInt(value);
                } catch (NumberFormatException exception) {
                    // handle invalid number
                }
                """, fix);
    }

    @Test
    void shouldImproveArithmeticSuggestion() {
        String fix = engine.improve(
                new ArithmeticException(),
                frame("number / divisor;"),
                baseHint("fallback")
        );

        assertEquals("""
                if (divisor != 0) {
                    int result = number / divisor;
                }
                """, fix);
    }

    @Test
    void shouldKeepFallbackWhenLineIsNotRecognized() {
        String fix = engine.improve(
                new NullPointerException(),
                frame("doSomething();"),
                baseHint("fallback")
        );

        assertEquals("fallback", fix);
    }

    private CodeFrame frame(String currentLine) {
        return new CodeFrame(true, 10, "", currentLine, "");
    }

    private ErrorHint baseHint(String fallback) {
        return new ErrorHint(
                "Error",
                "App.java:10",
                "reason",
                "suggestion",
                fallback,
                CodeFrame.unavailable()
        );
    }
}
