package codes.kevinhenriquez.logforge.enums;

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
public enum LogLevelEnum {
    DEBUG("DEBUG", 10),
    INFO("INFO", 20),
    SUCCESS("SUCCESS", 25),
    API("API", 30),
    WARNING("WARNING", 40),
    ERROR("ERROR", 50);

    private final String label;
    private final int priority;

    LogLevelEnum(String label, int priority) {
        this.label = label;
        this.priority = priority;
    }

    public String getLabel() {
        return label;
    }

    public int getPriority() {
        return priority;
    }
}
