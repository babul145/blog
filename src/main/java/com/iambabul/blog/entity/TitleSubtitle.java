package com.iambabul.blog.entity;

import com.iambabul.blog.util.BlogStatus;
import com.iambabul.blog.util.EntityUtils;
import com.iambabul.blog.util.UtilBase;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class TitleSubtitle extends EntityBase {
    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String subtitle;

    private Date created;
    private Date updated;

    private BlogStatus status = BlogStatus.ACTIVE;
}
