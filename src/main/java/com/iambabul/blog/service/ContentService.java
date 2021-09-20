package com.iambabul.blog.service;

import com.iambabul.blog.entity.BlogResponse;
import com.iambabul.blog.entity.Content;
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
    private final EntityManager entityManager;

    public List<Content> getContents() {
        log.info("getContents");
        try {
            return contentRepository.findAll(Sort.by(getText("created")).descending());
        }
        catch (Exception ex) {
            log.error(ex.getMessage());
            throw ex;
        }
    }

    public BlogResponse postContents(Content content) {
        log.info("postContents");
        BlogResponse blogResponse = null;
        try {
            content.collectAndSetCreateUpdateDate();
            contentRepository.save(content);
            blogResponse = getBlogResponse(Constants.RESPONSE_TYPE_SUCCESS, Content.class.getSimpleName(), null);
            return blogResponse;
        }
        catch (Exception ex) {
            log.error(ex.getMessage());
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
            response = new BlogResponse(getText("failed"), getText("failed.to.update.x0-x1", Content.class.getSimpleName(), ex.getMessage()));
            return response;
        }
    }

    public BlogResponse deleteContent(Long id) {
        log.info("deleteContent");
        BlogResponse response = null;
        try {
            if (id != null) {
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
            response = new BlogResponse(getText("failed"), getText("failed.to.delete.x0-x1", getText("content"), ex.getMessage()));
            return response;
        }
    }

    /*public Page<Content> get5Contents() {
        log.info("get5Contents");
        try {
            PageRequest pageRequest = PageRequest.of(Constants.PAGE_NUMBER_ZERO, Constants.PAGE_ITEM_SIZE_FIVE, Sort.by(Sort.Direction.DESC, getText("created")));
            Page<Content> page = contentRepository.findAll(pageRequest);
            return page;
        }
        catch (Exception ex) {
            log.error(ex.getMessage());
            throw ex;
        }
    }*/

    public List<Content> get5Contents() {
        log.info("get5Contents");
        try {
            List contents = contentRepository.findBy(ContentIdTitleCreated.class).stream().limit(5).collect(Collectors.toList());
            /*List<Content> contents = entityManager.createQuery("SELECT c.id, c.title FROM Content c ORDER BY c.created DESC", Content.class)
                    .getResultList();
            List<Content> contents = contentRepository.findFirst10ByCreated(new Date(), Sort.by("created"));*/
            return contents;
        }
        catch (Exception ex) {
            log.error(ex.getMessage());
            throw ex;
        }
    }
}
