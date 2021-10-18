package com.iambabul.blog.repository;

import com.iambabul.blog.entity.Content;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface ContentRepository extends JpaRepository<Content, Long> {
    <T> List<T> findBy(Class<T> projection);
}
