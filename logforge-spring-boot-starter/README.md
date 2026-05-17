# LogForge Spring Boot Starter

Basic Spring Boot integration for LogForge.

```xml
<dependency>
    <groupId>io.github.devkevingg</groupId>
    <artifactId>logforge-spring-boot-starter</artifactId>
    <version>0.2.0</version>
</dependency>
```

```properties
logforge.enabled=true
logforge.request-logging-enabled=true
logforge.startup-log-enabled=true
logforge.exception-analyzer-enabled=true
logforge.rest-error-formatting-enabled=true
logforge.include-error-timestamp=true
logforge.include-error-path=true
logforge.include-exception-name=false
```

LogForge can automatically analyze unhandled Spring exceptions and print detailed error hints to the console while returning a safe generic `500` response to clients.

When `logforge.rest-error-formatting-enabled=true`, the starter returns a structured error body with optional timestamp, path and exception name fields. Keep `logforge.include-exception-name=false` in production unless you explicitly want to expose the exception type to clients.
