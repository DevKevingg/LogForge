package io.github.devkevingg.logforge.spring;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.WebApplicationContextRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/*
 * © 2026 LogForge. All rights reserved.
 *
 * File              : LogForgeGlobalExceptionHandlerTest.java
 * Author            : Kevin Henriquez
 * Creation Date     : 17/05/2026
 *
 * Legal Notice:
 * Unauthorized copying, distribution, or modification of this
 * source code is strictly prohibited without explicit permission
 * from the author.
 * =============================================================================
 */
class LogForgeGlobalExceptionHandlerTest {
    private final WebApplicationContextRunner contextRunner = new WebApplicationContextRunner()
            .withConfiguration(AutoConfigurations.of(
                    LogForgeAutoConfiguration.class,
                    LogForgeWebAutoConfiguration.class
            ));

    @Test
    void contextLoadsWithExceptionAnalyzerEnabled() {
        contextRunner.run(context -> assertThat(context).hasSingleBean(LogForgeGlobalExceptionHandler.class));
    }

    @Test
    void handlerBeanExistsByDefaultInServletWebApp() {
        contextRunner.run(context -> assertThat(context).hasSingleBean(LogForgeGlobalExceptionHandler.class));
    }

    @Test
    void handlerBeanDoesNotExistWhenExceptionAnalyzerIsDisabled() {
        contextRunner
                .withPropertyValues("logforge.exception-analyzer-enabled=false")
                .run(context -> assertThat(context).doesNotHaveBean(LogForgeGlobalExceptionHandler.class));
    }

    @Test
    void formatsSafeRestErrorResponseByDefault() {
        LogForgeGlobalExceptionHandler handler = new LogForgeGlobalExceptionHandler(new LogForgeSpringProperties());
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/api/orders");

        ResponseEntity<?> response = handler.handleException(new IllegalStateException("sensitive"), request);

        assertThat(response.getStatusCode().value()).isEqualTo(500);
        assertThat(response.getBody()).isInstanceOf(LogForgeErrorResponse.class);

        LogForgeErrorResponse body = (LogForgeErrorResponse) response.getBody();
        assertThat(body.error()).isEqualTo("Internal Server Error");
        assertThat(body.message()).isEqualTo("An unexpected error occurred");
        assertThat(body.path()).isEqualTo("/api/orders");
        assertThat(body.timestamp()).isNotBlank();
        assertThat(body.exception()).isNull();
    }

    @Test
    void omitsOptionalFieldsWhenTheyAreDisabled() {
        LogForgeSpringProperties properties = new LogForgeSpringProperties();
        properties.setIncludeErrorTimestamp(false);
        properties.setIncludeErrorPath(false);
        properties.setIncludeExceptionName(true);

        LogForgeGlobalExceptionHandler handler = new LogForgeGlobalExceptionHandler(properties);
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/api/orders");

        ResponseEntity<?> response = handler.handleException(new IllegalStateException("sensitive"), request);
        LogForgeErrorResponse body = (LogForgeErrorResponse) response.getBody();

        assertThat(body.timestamp()).isNull();
        assertThat(body.path()).isNull();
        assertThat(body.exception()).isEqualTo("IllegalStateException");
    }

    @Test
    void fallsBackToSimpleSafeBodyWhenRestFormattingIsDisabled() {
        LogForgeSpringProperties properties = new LogForgeSpringProperties();
        properties.setRestErrorFormattingEnabled(false);

        LogForgeGlobalExceptionHandler handler = new LogForgeGlobalExceptionHandler(properties);
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/api/orders");

        ResponseEntity<?> response = handler.handleException(new IllegalStateException("sensitive"), request);

        assertThat(response.getStatusCode().value()).isEqualTo(500);
        assertThat(response.getBody()).isEqualTo(Map.of(
                "error", "Internal Server Error",
                "message", "An unexpected error occurred",
                "status", 500
        ));
    }
}
