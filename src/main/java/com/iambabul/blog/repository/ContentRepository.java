package com.iambabul.blog.repository;

import com.iambabul.blog.entity.Content;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentRepository extends JpaRepository<Content, Long> {
}
