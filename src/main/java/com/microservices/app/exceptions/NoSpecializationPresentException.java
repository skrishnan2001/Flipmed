package com.microservices.app.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NoSpecializationPresentException extends RuntimeException {

    public NoSpecializationPresentException(String message) {
        super(message);
    }

}
