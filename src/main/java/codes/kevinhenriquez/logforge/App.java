package codes.kevinhenriquez.logforge;

import codes.kevinhenriquez.logforge.config.LogForgeConfig;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
        LogForgeConfig.setColorsEnabled(false);
        LogForgeConfig.setIconsEnabled(false);
        LogForgeConfig.setTimestampEnabled(false);

        LogForge.success("Servidor iniciado en puerto {}", 8080);

        LogForgeConfig.reset();

        LogForge.success("Configuración restaurada");
    }
}
