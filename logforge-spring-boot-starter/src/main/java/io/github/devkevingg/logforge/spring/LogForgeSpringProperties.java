package io.github.devkevingg.logforge.spring;

import org.springframework.boot.context.properties.ConfigurationProperties;

/*
 * © 2026 LogForge. All rights reserved.
 *
 * File              : LogForgeSpringProperties.java
 * Author            : Kevin Henriquez
 * Creation Date     : 17/05/2026
 *
 * Legal Notice:
 * Unauthorized copying, distribution, or modification of this
 * source code is strictly prohibited without explicit permission
 * from the author.
 * =============================================================================
 */
@ConfigurationProperties(prefix = "logforge")
public class LogForgeSpringProperties {
    private boolean enabled = true;
    private boolean requestLoggingEnabled = true;
    private boolean startupLogEnabled = true;
    private boolean errorHintsEnabled = true;
    private boolean exceptionAnalyzerEnabled = true;
    private boolean colorsEnabled = true;
    private boolean iconsEnabled = true;
    private boolean timestampEnabled = true;
    private boolean restErrorFormattingEnabled = true;
    private boolean includeErrorTimestamp = true;
    private boolean includeErrorPath = true;
    private boolean includeExceptionName = false;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isRequestLoggingEnabled() {
        return requestLoggingEnabled;
    }

    public void setRequestLoggingEnabled(boolean requestLoggingEnabled) {
        this.requestLoggingEnabled = requestLoggingEnabled;
    }

    public boolean isStartupLogEnabled() {
        return startupLogEnabled;
    }

    public void setStartupLogEnabled(boolean startupLogEnabled) {
        this.startupLogEnabled = startupLogEnabled;
    }

    public boolean isErrorHintsEnabled() {
        return errorHintsEnabled;
    }

    public void setErrorHintsEnabled(boolean errorHintsEnabled) {
        this.errorHintsEnabled = errorHintsEnabled;
    }

    public boolean isExceptionAnalyzerEnabled() {
        return exceptionAnalyzerEnabled;
    }

    public void setExceptionAnalyzerEnabled(boolean exceptionAnalyzerEnabled) {
        this.exceptionAnalyzerEnabled = exceptionAnalyzerEnabled;
    }

    public boolean isColorsEnabled() {
        return colorsEnabled;
    }

    public void setColorsEnabled(boolean colorsEnabled) {
        this.colorsEnabled = colorsEnabled;
    }

    public boolean isIconsEnabled() {
        return iconsEnabled;
    }

    public void setIconsEnabled(boolean iconsEnabled) {
        this.iconsEnabled = iconsEnabled;
    }

    public boolean isTimestampEnabled() {
        return timestampEnabled;
    }

    public void setTimestampEnabled(boolean timestampEnabled) {
        this.timestampEnabled = timestampEnabled;
    }

    public boolean isRestErrorFormattingEnabled() {
        return restErrorFormattingEnabled;
    }

    public void setRestErrorFormattingEnabled(boolean restErrorFormattingEnabled) {
        this.restErrorFormattingEnabled = restErrorFormattingEnabled;
    }

    public boolean isIncludeErrorTimestamp() {
        return includeErrorTimestamp;
    }

    public void setIncludeErrorTimestamp(boolean includeErrorTimestamp) {
        this.includeErrorTimestamp = includeErrorTimestamp;
    }

    public boolean isIncludeErrorPath() {
        return includeErrorPath;
    }

    public void setIncludeErrorPath(boolean includeErrorPath) {
        this.includeErrorPath = includeErrorPath;
    }

    public boolean isIncludeExceptionName() {
        return includeExceptionName;
    }

    public void setIncludeExceptionName(boolean includeExceptionName) {
        this.includeExceptionName = includeExceptionName;
    }
}
