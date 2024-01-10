package dev.application.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import dev.application.util.ValidationError;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

@ApplicationScoped
public class ValidationServiceImpl implements ValidationService {

    private final Validator validator;

    public ValidationServiceImpl() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    @Override
    public <T> List<ValidationError> validate(T object) {
        Set<ConstraintViolation<T>> violations = validator.validate(object);

        List<ValidationError> errors = new ArrayList<>();
        for (ConstraintViolation<T> violation : violations) {
            ValidationError error = new ValidationError(
                    violation.getPropertyPath().toString(),
                    violation.getMessage());
            errors.add(error);
        }

        return errors;
    }
}