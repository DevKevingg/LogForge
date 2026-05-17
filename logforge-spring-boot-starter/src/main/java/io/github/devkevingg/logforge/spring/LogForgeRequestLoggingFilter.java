package io.github.devkevingg.logforge.spring;

import codes.kevinhenriquez.logforge.LogForge;
import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/*
 * © 2026 LogForge. All rights reserved.
 *
 * File              : LogForgeRequestLoggingFilter.java
 * Author            : Kevin Henriquez
 * Creation Date     : 17/05/2026
 *
 * Legal Notice:
 * Unauthorized copying, distribution, or modification of this
 * source code is strictly prohibited without explicit permission
 * from the author.
 * =============================================================================
 */
public class LogForgeRequestLoggingFilter extends OncePerRequestFilter {
    private final LogForgeSpringProperties properties;

    public LogForgeRequestLoggingFilter(LogForgeSpringProperties properties) {
        this.properties = properties;
    }

    @Override
    protected void doFilterInternal(
            @Nonnull HttpServletRequest request,
            @Nonnull HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        long start = System.currentTimeMillis();

        try {
            filterChain.doFilter(request, response);
        } finally {
            if (properties.isRequestLoggingEnabled()) {
                LogForge.api(
                        request.getMethod(),
                        request.getRequestURI(),
                        response.getStatus(),
                        System.currentTimeMillis() - start
                );
            }
        }
    }
}
