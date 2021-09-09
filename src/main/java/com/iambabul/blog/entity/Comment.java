package com.iambabul.blog.entity;

import com.iambabul.blog.util.EntityUtils;
import com.iambabul.blog.util.UtilBase;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Data
@Entity
public class Comment extends UtilBase implements EntityUtils {
    @Id
    @GeneratedValue
    private Long id;
    private String text;

    private Date created;
    private Date updated;

    @Override
    public void collectAndSetCreateUpdateDate() {
        this.setCreated(new Date());
        this.setUpdated(new Date());
    }

    @Override
    public String getEntityName() {
        return getText("comment");
    }
}
