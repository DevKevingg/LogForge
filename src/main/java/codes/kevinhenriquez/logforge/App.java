package codes.kevinhenriquez.logforge;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
        LogForge.success("Servidor iniciado en puerto {}", 8080);

        LogForge.warning(
                "Usuario {} intentó acceder a {}",
                "kevin",
                "/admin"
        );

        LogForge.api(
                "POST",
                "/api/orders",
                201,
                42
        );

        try {
            throw new RuntimeException("Base de datos caída");
        } catch (Exception e) {
            LogForge.error(
                    "No se pudo conectar con {}",
                    e,
                    "PostgreSQL"
            );
        }

        LogForge.time("Cargar usuarios", () -> {
            Thread.sleep(250);
        });
    }
}
