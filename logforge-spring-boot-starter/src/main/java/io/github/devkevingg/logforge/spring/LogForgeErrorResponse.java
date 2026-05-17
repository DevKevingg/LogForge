package io.github.devkevingg.logforge.spring;

import java.time.Instant;

/*
 * © 2026 LogForge. All rights reserved.
 *
 * File              : LogForgeErrorResponse.java
 * Author            : Kevin Henriquez
 * Creation Date     : 17/05/2026
 *
 * Legal Notice:
 * Unauthorized copying, distribution, or modification of this
 * source code is strictly prohibited without explicit permission
 * from the author.
 * =============================================================================
 */
public record LogForgeErrorResponse(
        String timestamp,
        int status,
        String error,
        String message,
        String path,
        String exception) {

    public static LogForgeErrorResponse internalServerError(
            LogForgeSpringProperties properties,
            String path,
            Throwable throwable) {
        return new LogForgeErrorResponse(properties.isIncludeErrorTimestamp() ? Instant.now().toString() : null,
                500,
                "Internal Server Error",
                "An unexpected error occurred",
                properties.isIncludeErrorPath() ? path : null,
                properties.isIncludeExceptionName() && throwable != null ? throwable.getClass().getSimpleName() : null
        );
    }
}
