package com.poppulo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class LineException extends RuntimeException {
    public LineException(String message) {
        super(message);
    }
}
