package com.iambabul.blog.service;

import com.iambabul.blog.entity.BlogResponse;
import com.iambabul.blog.entity.Content;
import com.iambabul.blog.exception.BlogException;
import com.iambabul.blog.projection.ContentIdTitleCreated;
import com.iambabul.blog.repository.ContentRepository;
import com.iambabul.blog.util.Constants;
import com.iambabul.blog.util.UtilBase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ContentService extends UtilBase {
    private final ContentRepository contentRepository;

    public List<Content> getContents() {
        try {
            log.info("Fetching all contents");
            return contentRepository.findAll(Sort.by(getText("created")).descending());
        }
        catch (Exception ex) {
            log.error(ex.getMessage());
            throw new BlogException(getText("failed.to.load.content-x0", ex.getMessage()));
        }
    }

    public BlogResponse postContents(Content content) {
        BlogResponse blogResponse = null;
        try {
            log.info("Saving content {} to the database", content.getId());
            content.collectAndSetCreateUpdateDate();
            contentRepository.save(content);
            blogResponse = getBlogResponse(Constants.RESPONSE_TYPE_SUCCESS, Content.class.getSimpleName(), null);
            return blogResponse;
        }
        catch (Exception ex) {
            log.error(ex.getMessage());
            throw new BlogException(getText("failed.to.save.content-x0", ex.getMessage()));
        }
    }

    public Content getContent(Long id) {
        try {
            log.info("Fetching content {}", id);
            return contentRepository.findById(id)
                    .orElseThrow(() -> new IllegalStateException("No found"));
        }
        catch (Exception ex) {
            log.error(ex.getMessage());
            throw new BlogException(getText("failed.to.load.content-x0", ex.getMessage()));
        }
    }

    public BlogResponse putContent(Content content) {
        BlogResponse response = null;
        try {
            log.info("Updating content {}", content.getId());
            Content existingContent = contentRepository.findById(content.getId())
                    .orElseThrow(() -> new IllegalStateException(getText("no.found")));
            if (existingContent != null) {
                content.collectCreateDateAndSetUpdateDate(existingContent.getCreated());
                contentRepository.save(content);
                response = new BlogResponse(getText("success"), getText("x0.has.been.updated.successfully", getText("content")));
            }
            else {
                response = new BlogResponse(getText("failed"), getText("failed.to.update.x0-x1", getText("content"), "No found"));
            }
            return response;
        }
        catch (Exception ex) {
            log.error(ex.getMessage());
            throw new BlogException(getText("failed.to.update.content-x0", ex.getMessage()));
        }
    }

    public BlogResponse deleteContent(Long id) {
        BlogResponse response = null;
        try {
            if (id != null) {
                log.info("Deleting content {}", id);
                contentRepository.deleteById(id);
                response = new BlogResponse(getText("success"), getText("x0.has.been.deleted.successfully", getText("content")));
            }
            else {
                response = new BlogResponse(getText("failed"), getText("failed.to.delete.x0-x1","No found"));
            }
            return response;
        }
        catch (Exception ex) {
            log.error(ex.getMessage());
            throw new BlogException(getText("failed.to.delete.content-x0", ex.getMessage()));
        }
    }

    public List<Content> get5Contents() {
        try {
            log.info("Fetching 5 contents");
            List contents = contentRepository.findBy(ContentIdTitleCreated.class).stream().limit(5).collect(Collectors.toList());
            return contents;
        }
        catch (Exception ex) {
            log.error(ex.getMessage());
            throw new BlogException(getText("failed.to.load.content-x0", ex.getMessage()));
        }
    }
}
