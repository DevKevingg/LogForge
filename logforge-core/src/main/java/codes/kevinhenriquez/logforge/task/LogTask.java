package codes.kevinhenriquez.logforge.task;

/*
 * © 2026 LogForge. All rights reserved.
 *
 * File              : LogTask.java
 * Author            : Kevin Henriquez
 * Creation Date     : 16/05/2026
 *
 * Legal Notice:
 * Unauthorized copying, distribution, or modification of this
 * source code is strictly prohibited without explicit permission
 * from the author.
 * =============================================================================
 */
@FunctionalInterface
public interface LogTask {
    void run() throws Exception;
}
