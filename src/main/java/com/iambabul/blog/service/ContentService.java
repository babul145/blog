package com.iambabul.blog.service;

import com.iambabul.blog.entity.Content;
import com.iambabul.blog.repository.ContentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service @Slf4j
public class ContentService {
    private final ContentRepository contentRepository;

    @Autowired
    public ContentService(ContentRepository contentRepository) {
        this.contentRepository = contentRepository;
    }

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
