package codes.kevinhenriquez.logforge.output;

/*
 * © 2026 LogForge. All rights reserved.
 *
 * File              : ConsoleWriter.java
 * Author            : Kevin Henriquez
 * Creation Date     : 16/05/2026
 *
 * Legal Notice:
 * Unauthorized copying, distribution, or modification of this
 * source code is strictly prohibited without explicit permission
 * from the author.
 * =============================================================================
 */
public class ConsoleWriter {

    public void writeLog(String message) {
        System.out.println(message);
    }

    public void errorLog(String message) {
        System.out.println(message);
    }

    public void debugLog(String message) {
        System.out.println(message);
    }
}
