package io.github.devkevingg.logforge.spring;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.OncePerRequestFilter;

/*
 * © 2026 LogForge. All rights reserved.
 *
 * File              : LogForgeWebAutoConfiguration.java
 * Author            : Kevin Henriquez
 * Creation Date     : 17/05/2026
 *
 * Legal Notice:
 * Unauthorized copying, distribution, or modification of this
 * source code is strictly prohibited without explicit permission
 * from the author.
 * =============================================================================
 */
@AutoConfiguration(after = LogForgeAutoConfiguration.class)
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@ConditionalOnClass(OncePerRequestFilter.class)
public class LogForgeWebAutoConfiguration {

    @Bean
    @ConditionalOnProperty(
            prefix = "logforge",
            name = "request-logging-enabled",
            havingValue = "true",
            matchIfMissing = true
    )
    LogForgeRequestLoggingFilter logForgeRequestLoggingFilter(LogForgeSpringProperties properties) {
        return new LogForgeRequestLoggingFilter(properties);
    }

    @Bean
    @ConditionalOnProperty(
            prefix = "logforge",
            name = "exception-analyzer-enabled",
            havingValue = "true",
            matchIfMissing = true
    )
    LogForgeGlobalExceptionHandler logForgeGlobalExceptionHandler(LogForgeSpringProperties properties) {
        return new LogForgeGlobalExceptionHandler(properties);
    }
}
