package com.microservices.app.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class PatientAlreadyOccupiedException extends RuntimeException {

    public PatientAlreadyOccupiedException(String message) {
        super(message);
    }

}
