package com.iambabul.blog.exception;

import lombok.Data;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Data
public class BlogException extends RuntimeException {

    Integer statusCode = INTERNAL_SERVER_ERROR.value();

    public BlogException() {
        super("unknown exception has occurred");
    }

    public BlogException(String message) {
        super(message);
    }

    public BlogException(String message, Integer statusCode) {
        super(message);
        this.statusCode = statusCode;
    }
}
