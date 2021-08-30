package com.iambabul.blog.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class BlogResponse {
    private String result;
    private String message;
}
