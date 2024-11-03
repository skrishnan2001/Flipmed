package com.microservices.app.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class DoctorNotPresentException extends RuntimeException {

    public DoctorNotPresentException(String message) {
        super(message);
    }

}
