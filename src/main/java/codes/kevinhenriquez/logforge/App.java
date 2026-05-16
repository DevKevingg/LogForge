package codes.kevinhenriquez.logforge;

import codes.kevinhenriquez.logforge.config.LogForgeConfig;
import codes.kevinhenriquez.logforge.enums.LogLevelEnum;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
        LogForge.table(
                new String[]{"ID", "User", "Role"},
                new String[][]{
                        {"1", "Kevin", "Admin"},
                        {"2", "Kevin", "User"},
                })
        ;
    }
}
