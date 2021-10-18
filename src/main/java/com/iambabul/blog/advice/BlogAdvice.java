package com.iambabul.blog.advice;

import com.iambabul.blog.exception.BlogException;
import com.iambabul.blog.pojo.BlogError;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class BlogAdvice {

    @ExceptionHandler(BlogException.class)
    public ResponseEntity<BlogError> mapWithBlogError(BlogException ex) {
        BlogError error = new BlogError(ex.getStatusCode(), ex.getMessage());
        return ResponseEntity.badRequest().body(error);
    }
}
