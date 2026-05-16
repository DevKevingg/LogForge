package codes.kevinhenriquez.logforge;

import codes.kevinhenriquez.logforge.config.LogForgeConfig;
import codes.kevinhenriquez.logforge.enums.LogLevelEnum;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
        LogForgeConfig.setMinimumLevel(LogLevelEnum.WARNING);

        LogForge.debug("Esto no debe salir");
        LogForge.info("Esto tampoco");
        LogForge.success("Esto tampoco");
        LogForge.warning("Esto sí sale");
        LogForge.error("Esto también sale");

        LogForgeConfig.reset();
    }
}
