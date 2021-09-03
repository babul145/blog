package com.iambabul.blog.entity;

import com.iambabul.blog.util.EntityUtils;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Entity @Data
public class Content implements EntityUtils {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;

    @Size(max = 99999)
    private String text;
    private Date created;
    private Date updated;

    @OneToMany
    private List<Comment> comments;

    @Override
    public void collectAndSetCreateUpdateDate() {
        this.setCreated(new Date());
        this.setUpdated(new Date());
    }
}
