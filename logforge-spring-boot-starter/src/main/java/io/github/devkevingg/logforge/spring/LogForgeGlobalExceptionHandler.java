package io.github.devkevingg.logforge.spring;

import codes.kevinhenriquez.logforge.LogForge;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/*
 * © 2026 LogForge. All rights reserved.
 *
 * File              : LogForgeGlobalExceptionHandler.java
 * Author            : Kevin Henriquez
 * Creation Date     : 17/05/2026
 *
 * Legal Notice:
 * Unauthorized copying, distribution, or modification of this
 * source code is strictly prohibited without explicit permission
 * from the author.
 * =============================================================================
 */
@RestControllerAdvice
@ConditionalOnProperty(prefix = "logforge", name = "exception-analyzer-enabled", havingValue = "true", matchIfMissing = true)
public class LogForgeGlobalExceptionHandler {
    private final LogForgeSpringProperties properties;

    public LogForgeGlobalExceptionHandler(LogForgeSpringProperties properties) {
        this.properties = properties;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(
            Exception exception,
            HttpServletRequest request) {
        LogForge.explain(exception);

        if (!properties.isRestErrorFormattingEnabled()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(java.util.Map.of(
                            "error", "Internal Server Error",
                            "message", "An unexpected error occurred",
                            "status", 500
                    ));
        }

        LogForgeErrorResponse body = LogForgeErrorResponse.internalServerError(
                properties,
                request.getRequestURI(),
                exception
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }
}
