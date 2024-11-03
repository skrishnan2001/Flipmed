package com.microservices.app.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class DoctorAlreadyPresentException extends RuntimeException {

    public DoctorAlreadyPresentException(String message) {
        super(message);
    }

}
