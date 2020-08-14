package com.poppulo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.sql.SQLException;

//@ControllerAdvice
public class ServiceErrorAdvice {
    private final Logger logger = LoggerFactory.getLogger(ServiceErrorAdvice.class);

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({SQLException.class, NullPointerException.class})
    public void handle() {
        logger.info("handing exception");
    }
}