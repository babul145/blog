package com.iambabul.blog.repository;

import com.iambabul.blog.entity.Content;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ContentRepository extends JpaRepository<Content, Long> {
}
