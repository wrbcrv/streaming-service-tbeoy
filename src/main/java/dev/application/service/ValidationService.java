package dev.application.service;

import java.util.List;

import dev.application.util.ValidationError;

public interface ValidationService {

    <T> List<ValidationError> validate(T object);
}