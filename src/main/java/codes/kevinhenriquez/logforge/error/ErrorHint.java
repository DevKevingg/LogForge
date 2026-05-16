package codes.kevinhenriquez.logforge.error;

/*
 * © 2026 LogForge. All rights reserved.
 *
 * File              : ErrorHint.java
 * Author            : Kevin Henriquez
 * Creation Date     : 16/05/2026
 *
 * Legal Notice:
 * Unauthorized copying, distribution, or modification of this
 * source code is strictly prohibited without explicit permission
 * from the author.
 * =============================================================================
 */
public record ErrorHint(
        String errorName,
        String location,
        String reason,
        String suggestion,
        String exampleFix,
        CodeFrame codeFrame
) {
}
