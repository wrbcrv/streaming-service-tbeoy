package dev.application.util;

import java.util.List;

public class ValidationException extends Exception {

    private List<ValidationError> validationErrors;

    public ValidationException(List<ValidationError> validationErrors) {
        this.validationErrors = validationErrors;
    }

    public List<ValidationError> getValidationErrors() {
        return validationErrors;
    }
}