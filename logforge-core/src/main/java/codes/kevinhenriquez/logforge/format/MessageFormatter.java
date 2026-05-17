package codes.kevinhenriquez.logforge.format;

/*
 * © 2026 LogForge. All rights reserved.
 *
 * File              : MessageFormatter.java
 * Author            : Kevin Henriquez
 * Creation Date     : 16/05/2026
 *
 * Legal Notice:
 * Unauthorized copying, distribution, or modification of this
 * source code is strictly prohibited without explicit permission
 * from the author.
 * =============================================================================
 */
public class MessageFormatter {
    public static String format(String template, Object... args) {
        if (template == null) {
            return "null";
        }

        if (args.length == 0 || args[0] == null) {
            return template;
        }

        StringBuilder result = new StringBuilder();
        int argsIndex = 0;

        for (int i = 0; i < template.length(); i++) {
            if (i < template.length() -1
                    && template.charAt(i) == '{'
                    && template.charAt(i + 1) == '}'
                    && argsIndex < args.length) {
                result.append(args[argsIndex++]);
                i++;
            } else {
                result.append(template.charAt(i));
            }
        }

        return result.toString();
    }
}
