package com.iambabul.blog.entity;

import com.iambabul.blog.util.BlogStatus;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

import static javax.persistence.FetchType.EAGER;
import static javax.persistence.GenerationType.AUTO;

@Entity @Data
public class Content extends EntityBase {
    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;
    private String title;
    @Size(max = 99999)
    private String text;
    private Date created;
    private Date updated;
    @OneToMany(fetch = EAGER)
    private List<Comment> comments;
    private BlogStatus status = BlogStatus.ACTIVE;
}
