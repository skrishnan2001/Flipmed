package com.microservices.app.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class PatientAlreadyPresentException extends RuntimeException {

    public PatientAlreadyPresentException(String message) {
        super(message);
    }

}
