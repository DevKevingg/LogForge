package codes.kevinhenriquez.logforge.error;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/*
 * © 2026 LogForge. All rights reserved.
 *
 * File              : SourceCodeReader.java
 * Author            : Kevin Henriquez
 * Creation Date     : 16/05/2026
 *
 * Legal Notice:
 * Unauthorized copying, distribution, or modification of this
 * source code is strictly prohibited without explicit permission
 * from the author.
 * =============================================================================
 */
public class SourceCodeReader {
    private final Path projectRoot;

    public SourceCodeReader() {
        this(Path.of("").toAbsolutePath().normalize());
    }

    SourceCodeReader(Path projectRoot) {
        this.projectRoot = projectRoot;
    }

    public CodeFrame read(String fileName, int lineNumber) {
        if (fileName == null || fileName.isBlank() || lineNumber < 1) {
            return CodeFrame.unavailable();
        }

        Optional<Path> sourceFile = findSourceFile(fileName);
        if (sourceFile.isEmpty()) {
            return CodeFrame.unavailable();
        }

        try {
            List<String> lines = Files.readAllLines(sourceFile.get());
            if (lineNumber > lines.size()) {
                return CodeFrame.unavailable();
            }

            String previousLine = lineNumber > 1
                    ? lines.get(lineNumber - 2)
                    : "";
            String currentLine = lines.get(lineNumber - 1);
            String nextLine = lineNumber < lines.size()
                    ? lines.get(lineNumber)
                    : "";

            return new CodeFrame(true, lineNumber, previousLine, currentLine, nextLine);
        } catch (IOException exception) {
            return CodeFrame.unavailable();
        }
    }

    private Optional<Path> findSourceFile(String fileName) {
        Optional<Path> mainSource = findByFileName(projectRoot.resolve("src/main/java"), fileName);
        if (mainSource.isPresent()) {
            return mainSource;
        }

        return findByFileName(projectRoot.resolve("src/test/java"), fileName);
    }

    private Optional<Path> findByFileName(Path root, String fileName) {
        if (!Files.exists(root)) {
            return Optional.empty();
        }

        try (Stream<Path> paths = Files.walk(root)) {
            return paths
                    .filter(Files::isRegularFile)
                    .filter(path -> fileName.equals(path.getFileName().toString()))
                    .findFirst();
        } catch (IOException exception) {
            return Optional.empty();
        }
    }
}
