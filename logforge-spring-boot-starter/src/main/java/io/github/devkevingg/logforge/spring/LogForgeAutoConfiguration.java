package io.github.devkevingg.logforge.spring;

import codes.kevinhenriquez.logforge.LogForge;
import codes.kevinhenriquez.logforge.config.LogForgeConfig;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/*
 * © 2026 LogForge. All rights reserved.
 *
 * File              : LogForgeAutoConfiguration.java
 * Author            : Kevin Henriquez
 * Creation Date     : 17/05/2026
 *
 * Legal Notice:
 * Unauthorized copying, distribution, or modification of this
 * source code is strictly prohibited without explicit permission
 * from the author.
 * =============================================================================
 */
@AutoConfiguration
@EnableConfigurationProperties(LogForgeSpringProperties.class)
@ConditionalOnClass(LogForge.class)
@ConditionalOnProperty(prefix = "logforge", name = "enabled", havingValue = "true", matchIfMissing = true)
public class LogForgeAutoConfiguration {

    @Bean
    ApplicationRunner logForgeApplicationRunner(LogForgeSpringProperties properties) {
        return args -> {
            LogForgeConfig.setEnabled(properties.isEnabled());
            LogForgeConfig.setColorsEnabled(properties.isColorsEnabled());
            LogForgeConfig.setIconsEnabled(properties.isIconsEnabled());
            LogForgeConfig.setTimestampEnabled(properties.isTimestampEnabled());

            if (properties.isStartupLogEnabled()) {
                LogForge.success("LogForge Spring Boot starter initialized");
            }
        };
    }
}
