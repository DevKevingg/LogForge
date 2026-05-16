package codes.kevinhenriquez.logforge.error;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/*
 * © 2026 LogForge. All rights reserved.
 *
 * File              : SourceCodeReaderTest.java
 * Author            : Kevin Henriquez
 * Creation Date     : 16/05/2026
 *
 * Legal Notice:
 * Unauthorized copying, distribution, or modification of this
 * source code is strictly prohibited without explicit permission
 * from the author.
 * =============================================================================
 */
class SourceCodeReaderTest {

    @TempDir
    Path tempDir;

    @Test
    void shouldReadContextFromMainSourceDirectory() throws IOException {
        Path sourceFile = tempDir.resolve("src/main/java/example/App.java");
        Files.createDirectories(sourceFile.getParent());
        Files.writeString(sourceFile, """
                class App {
                    void run() {
                        value.toLowerCase();
                    }
                }
                """);

        SourceCodeReader reader = new SourceCodeReader(tempDir);
        CodeFrame frame = reader.read("App.java", 3);

        assertTrue(frame.available());
        assertEquals("    void run() {", frame.previousLine());
        assertEquals("        value.toLowerCase();", frame.currentLine());
        assertEquals("    }", frame.nextLine());
    }

    @Test
    void shouldReturnUnavailableWhenFileDoesNotExist() {
        SourceCodeReader reader = new SourceCodeReader(tempDir);

        CodeFrame frame = reader.read("Missing.java", 10);

        assertFalse(frame.available());
    }
}
