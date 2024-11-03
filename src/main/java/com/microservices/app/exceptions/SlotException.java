package com.microservices.app.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class SlotException extends RuntimeException {

    public SlotException(String message) {
        super(message);
    }

}
