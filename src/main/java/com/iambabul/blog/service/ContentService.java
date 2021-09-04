package com.iambabul.blog.service;

import com.iambabul.blog.entity.BlogResponse;
import com.iambabul.blog.entity.Content;
import com.iambabul.blog.repository.ContentRepository;
import com.iambabul.blog.util.Constants;
import com.iambabul.blog.util.UtilBase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
            throw ex;
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

    public Content getContent(Long id) {
        log.info("getContent");
        try {
            return contentRepository.findById(id)
                    .orElseThrow(() -> new IllegalStateException("No found"));
        }
        catch (Exception ex) {
            log.error(ex.getMessage());
            throw ex;
        }
    }

    public BlogResponse putContent(Content content) {
        log.info("putContent");
        BlogResponse response = null;
        try {
            Content existingContent = contentRepository.findById(content.getId())
                    .orElseThrow(() -> new IllegalStateException("No found"));
            if (existingContent != null) {
                contentRepository.save(content);
                response = new BlogResponse(getMessage("success"), getMessage("x0.has.been.updated.successfully", content.getEntityName()));
            }
            else {
                response = new BlogResponse(getMessage("failed"), getMessage("failed.to.update.x0-x1",getMessage("content"), "No found"));
            }
            return response;
        }
        catch (Exception ex) {
            log.error(ex.getMessage());
            response = new BlogResponse(getMessage("failed"), getMessage("failed.to.update.x0-x1", Content.class.getSimpleName(), ex.getMessage()));
            return response;
        }
    }

    public BlogResponse deleteContent(Long id) {
        log.info("deleteContent");
        BlogResponse response = null;
        try {
            if (id != null) {
                contentRepository.deleteById(id);
                response = new BlogResponse(getMessage("success"), getMessage("x0.has.been.deleted.successfully", getMessage("content")));
            }
            else {
                response = new BlogResponse(getMessage("failed"), getMessage("failed.to.delete.x0-x1","No found"));
            }
            return response;
        }
        catch (Exception ex) {
            log.error(ex.getMessage());
            response = new BlogResponse(getMessage("failed"), getMessage("failed.to.delete.x0-x1", getMessage("content"), ex.getMessage()));
            return response;
        }
    }
}
