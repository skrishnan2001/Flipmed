package com.microservices.app.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class BookingNotPresentException extends RuntimeException {

    public BookingNotPresentException(String message) {
        super(message);
    }

}
