package com.iambabul.blog.entity;

import com.iambabul.blog.util.UtilBase;
import lombok.Data;

import java.util.Date;

@Data
public abstract class EntityBase extends UtilBase {
    private Date created;
    private Date updated;

    public void collectAndSetCreateUpdateDate() {
        this.setCreated(new Date());
        this.setUpdated(new Date());
    }

    public void collectCreateDateAndSetUpdateDate(Date created) {
        this.setCreated(created);
        this.setUpdated(new Date());
    }
}
