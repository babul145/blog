package com.iambabul.blog.service;

import com.iambabul.blog.entity.BlogResponse;
import com.iambabul.blog.entity.Content;
import com.iambabul.blog.repository.ContentRepository;
import com.iambabul.blog.util.Constants;
import com.iambabul.blog.util.UtilBase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ContentService extends UtilBase {
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

    public BlogResponse postContents(Content content) {
        BlogResponse blogResponse = null;
        try {
            content.collectAndSetCreateUpdateDate();
            contentRepository.save(content);
            blogResponse = getBlogResponse(Constants.RESPONSE_TYPE_SUCCESS, Content.class.getSimpleName(), null);
            return blogResponse;
        }
        catch (Exception ex) {
            blogResponse = getBlogResponse(Constants.RESPONSE_TYPE_FAILED, Content.class.getSimpleName(), ex.getMessage());
            return blogResponse;
        }
    }
}
