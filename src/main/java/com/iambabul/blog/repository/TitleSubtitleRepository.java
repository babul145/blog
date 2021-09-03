package com.iambabul.blog.repository;

import com.iambabul.blog.entity.TitleSubtitle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TitleSubtitleRepository extends JpaRepository<TitleSubtitle, Long> {
    Optional<TitleSubtitle> findById(Long id);
}
