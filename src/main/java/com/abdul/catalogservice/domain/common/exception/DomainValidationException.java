package com.abdul.catalogservice.domain.common.exception;

public class DomainValidationException extends RuntimeException {
    public DomainValidationException(String message) {
        super(message);
    }
}
