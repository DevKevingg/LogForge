package codes.kevinhenriquez.logforge.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/*
 * © 2026 LogForge. All rights reserved.
 *
 * File              : LogLevelEnum.java
 * Author            : Kevin Henriquez
 * Creation Date     : 16/05/2026
 *
 * Legal Notice:
 * Unauthorized copying, distribution, or modification of this
 * source code is strictly prohibited without explicit permission
 * from the author.
 * =============================================================================
 */
@Getter
@AllArgsConstructor
public enum LogLevelEnum {
    DEBUG("DEBUG", 10),
    INFO("INFO", 20),
    SUCCESS("SUCCESS", 25),
    API("API", 30),
    WARNING("WARNING", 40),
    ERROR("ERROR", 50);

    private final String label;
    private final int priority;
}
