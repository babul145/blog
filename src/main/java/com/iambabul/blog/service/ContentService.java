package com.iambabul.blog.service;

import com.iambabul.blog.entity.Content;
import com.iambabul.blog.repository.ContentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ContentService {
    private final ContentRepository contentRepository;

    public List<Content> getContents() {
        try {
            return contentRepository.findAll();
        }
        catch (Exception ex) {
            log.error(ex.getMessage());
            return null;
        }
    }
}
