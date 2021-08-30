package com.iambabul.blog.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Data
@Entity
public class Comment {
    @Id
    @GeneratedValue
    private Long id;
    private String text;

    private Date created;
    private Date updated;
}
