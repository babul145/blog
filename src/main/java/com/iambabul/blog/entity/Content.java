package com.iambabul.blog.entity;

import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity @Data
public class Content {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;

    @Column(columnDefinition = "LONGTEXT")
    private String text;
    private Date created;
    private Date updated;

    @OneToMany
    private List<Comment> comments;
}
