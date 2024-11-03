package com.microservices.app.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class PatientNotPresentException extends RuntimeException {

    public PatientNotPresentException(String message) {
        super(message);
    }

}
