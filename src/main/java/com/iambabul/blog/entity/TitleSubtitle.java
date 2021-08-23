package com.iambabul.blog.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class TitleSubtitle {
    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String subtitle;
}
