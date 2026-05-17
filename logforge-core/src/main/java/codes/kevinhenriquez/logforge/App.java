package codes.kevinhenriquez.logforge;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
        try {
            String value = null;
            value.toLowerCase();
        } catch (Exception exception) {
            LogForge.explain(exception);
        }
    }
}
