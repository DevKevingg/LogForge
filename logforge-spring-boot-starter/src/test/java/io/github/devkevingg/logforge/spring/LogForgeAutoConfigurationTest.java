package io.github.devkevingg.logforge.spring;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import static org.assertj.core.api.Assertions.assertThat;

/*
 * © 2026 LogForge. All rights reserved.
 *
 * File              : LogForgeAutoConfigurationTest.java
 * Author            : Kevin Henriquez
 * Creation Date     : 17/05/2026
 *
 * Legal Notice:
 * Unauthorized copying, distribution, or modification of this
 * source code is strictly prohibited without explicit permission
 * from the author.
 * =============================================================================
 */
class LogForgeAutoConfigurationTest {
    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
            .withConfiguration(AutoConfigurations.of(LogForgeAutoConfiguration.class));

    @Test
    void contextLoads() {
        contextRunner.run(context -> assertThat(context).hasSingleBean(LogForgeSpringProperties.class));
    }

    @Test
    void propertiesBindCorrectly() {
        contextRunner
                .withPropertyValues(
                        "logforge.colors-enabled=false",
                        "logforge.icons-enabled=false",
                        "logforge.timestamp-enabled=false",
                        "logforge.startup-log-enabled=false"
                )
                .run(context -> {
                    LogForgeSpringProperties properties = context.getBean(LogForgeSpringProperties.class);
                    assertThat(properties.isColorsEnabled()).isFalse();
                    assertThat(properties.isIconsEnabled()).isFalse();
                    assertThat(properties.isTimestampEnabled()).isFalse();
                    assertThat(properties.isStartupLogEnabled()).isFalse();
                });
    }

    @Test
    void autoConfigurationIsDisabledWhenLogForgeIsDisabled() {
        contextRunner
                .withPropertyValues("logforge.enabled=false")
                .run(context -> assertThat(context).doesNotHaveBean(LogForgeSpringProperties.class));
    }
}
