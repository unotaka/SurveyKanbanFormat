package com.example.userservice.exception;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Custom exception to indicate that validation failed.
 * Can carry field-specific errors and/or general global errors.
 */
public class ValidationException extends RuntimeException {
    private final Map<String, String> errors; // Field-specific errors (e.g., "username": "Username is too short")
    private final List<String> globalErrors; // General errors not tied to a specific field

    public ValidationException(String message) {
        super(message);
        this.errors = Collections.emptyMap();
        this.globalErrors = Collections.singletonList(message);
    }

    public ValidationException(Map<String, String> errors) {
        super("Validation failed for one or more fields.");
        this.errors = Objects.requireNonNull(errors, "Errors map cannot be null");
        this.globalErrors = Collections.emptyList();
    }

    public ValidationException(List<String> globalErrors) {
        super("Validation failed.");
        this.globalErrors = Objects.requireNonNull(globalErrors, "Global errors list cannot be null");
        this.errors = Collections.emptyMap();
    }

    public ValidationException(String message, Map<String, String> errors) {
        super(message);
        this.errors = Objects.requireNonNull(errors, "Errors map cannot be null");
        this.globalErrors = Collections.emptyList(); // If field errors are provided, assume no global errors specifically for this constructor
    }

    public ValidationException(String message, List<String> globalErrors) {
        super(message);
        this.globalErrors = Objects.requireNonNull(globalErrors, "Global errors list cannot be null");
        this.errors = Collections.emptyMap(); // If global errors are provided, assume no field errors specifically for this constructor
    }

    public Map<String, String> getErrors() {
        return Collections.unmodifiableMap(errors);
    }

    public List<String> getGlobalErrors() {
        return Collections.unmodifiableList(globalErrors);
    }

    /**
     * Checks if this exception contains any validation errors (field-specific or global).
     * @return true if there are errors, false otherwise.
     */
    public boolean hasErrors() {
        return !errors.isEmpty() || !globalErrors.isEmpty();
    }

    @Override
    public String getMessage() {
        StringBuilder sb = new StringBuilder(super.getMessage());
        if (!errors.isEmpty()) {
            sb.append(" Field errors: ").append(errors);
        }
        if (!globalErrors.isEmpty()) {
            sb.append(" Global errors: ").append(globalErrors);
        }
        return sb.toString();
    }
}
