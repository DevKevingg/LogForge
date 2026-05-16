# LogForge

**LogForge** is a lightweight and clean console logging toolkit for Java applications.

It provides beautiful terminal logs, ANSI colors, placeholder formatting, API request logs, exception tracing, API request helpers and execution timers without heavy dependencies.

## Features

- Clean and readable console logs
- ANSI colored output
- Placeholder support using `{}`
- API request logging helper
- Exception logging with stack traces
- Execution timer utility
- Lightweight Java library
- Simple static API

## Preview

![LogForge Preview](assets/preview.png)

```txt
✓ SUCCESS 03:40:23 Server started on port 8080
⚠ WARNING 03:40:23 User kevin tried to access /admin
→ API     03:40:23 POST /api/orders 201 42ms
✕ ERROR   03:40:23 Failed to connect to PostgreSQL
java.lang.RuntimeException: Database unavailable
```

## Installation

For now, clone the repository and install it locally:

```bash
git clone https://github.com/DevKevingg/LogForge.git
cd logforge
mvn clean install
```

Then add LogForge to another Maven project:

```xml
<dependency>
    <groupId>codes.kevinhenriquez</groupId>
    <artifactId>logforge</artifactId>
    <version>0.1.0-SNAPSHOT</version>
</dependency>
```

## Usage

```java
import codes.kevinhenriquez.logforge.LogForge;

public class App {

    public static void main(String[] args) {
        LogForge.success("Server started on port {}", 8080);

        LogForge.warning("User {} tried to access {}", "kevin", "/admin");

        LogForge.api("POST", "/api/orders", 201, 42);

        try {
            throw new RuntimeException("Database unavailable");
        } catch (Exception exception) {
            LogForge.error("Failed to connect to {}", exception, "PostgreSQL");
        }

        LogForge.time("Load users", () -> {
            Thread.sleep(250);
        });
    }
}
```

## Available Methods

```java
LogForge.info("Application started");

LogForge.success("Server started on port {}", 8080);

LogForge.warning("User {} tried to access {}", "kevin", "/admin");

LogForge.debug("Debug mode enabled");

LogForge.api("GET", "/api/users", 200, 18);

LogForge.error("Failed to connect");

LogForge.error("Failed to connect to {}", exception, "PostgreSQL");

LogForge.time("Load users", () -> {
    Thread.sleep(250);
});
```

## Requirements

- Java 17+
- Maven 3.8+

## Project Status

LogForge is currently in early development.

Current version:

```txt
0.1.0-SNAPSHOT
```

## Roadmap

- Configurable themes
- Disable ANSI colors
- Disable icons
- JSON log output
- File writer support
- Spring Boot starter
- Request logging filter for Spring Boot apps
- Log level filtering
- Custom timestamp format
- Unit tests for formatter and placeholders

## Contributing

Contributions are welcome.

You can help by:

- Reporting bugs
- Suggesting new features
- Improving documentation
- Adding tests
- Creating examples

## License

MIT License
