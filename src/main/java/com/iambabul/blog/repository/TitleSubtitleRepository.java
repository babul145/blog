package com.iambabul.blog.repository;

import com.iambabul.blog.entity.TitleSubtitle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TitleSubtitleRepository extends JpaRepository<TitleSubtitle, Long> {
    Optional<TitleSubtitle> findById(Long id);
}
