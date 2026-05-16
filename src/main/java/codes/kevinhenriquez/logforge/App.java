package codes.kevinhenriquez.logforge;

import codes.kevinhenriquez.logforge.config.LogForgeConfig;
import codes.kevinhenriquez.logforge.enums.LogLevelEnum;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
        LogForgeConfig.setEnabled(false);

        LogForge.success("Esto no sale");
        LogForge.error("Esto tampoco");

        LogForgeConfig.reset();
    }
}
