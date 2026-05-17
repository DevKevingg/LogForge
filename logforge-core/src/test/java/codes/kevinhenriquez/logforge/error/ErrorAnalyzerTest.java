package codes.kevinhenriquez.logforge.error;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/*
 * © 2026 LogForge. All rights reserved.
 *
 * File              : ErrorAnalyzerTest.java
 * Author            : Kevin Henriquez
 * Creation Date     : 16/05/2026
 *
 * Legal Notice:
 * Unauthorized copying, distribution, or modification of this
 * source code is strictly prohibited without explicit permission
 * from the author.
 * =============================================================================
 */
class ErrorAnalyzerTest {
    private final ErrorAnalyzer analyzer = new ErrorAnalyzer();

    @Test
    void shouldAnalyzeNullPointerException() {
        ErrorHint hint = analyzer.analyze(new NullPointerException());

        assertEquals("NullPointerException", hint.errorName());
    }

    @Test
    void shouldExplainIndexOutOfBoundsException() {
        ErrorHint hint = analyzer.analyze(new IndexOutOfBoundsException());

        assertTrue(hint.reason().contains("position that does not exist"));
    }

    @Test
    void shouldExplainNumberFormatException() {
        ErrorHint hint = analyzer.analyze(new NumberFormatException());

        assertTrue(hint.reason().contains("convert text into a number"));
    }

    @Test
    void shouldHandleNullThrowable() {
        ErrorHint hint = analyzer.analyze(null);

        assertEquals("Unknown error", hint.errorName());
        assertEquals("Unknown location", hint.location());
    }

    @Test
    void shouldUseUnknownLocationWhenStacktraceIsEmpty() {
        NullPointerException exception = new NullPointerException();
        exception.setStackTrace(new StackTraceElement[0]);

        ErrorHint hint = analyzer.analyze(exception);

        assertEquals("Unknown location", hint.location());
    }

    @Test
    void shouldAnalyzeRootCause() {
        RuntimeException wrapped = new RuntimeException("wrapped", new IllegalStateException("bad state"));

        ErrorHint hint = analyzer.analyze(wrapped);

        assertEquals("IllegalStateException", hint.errorName());
    }
}
