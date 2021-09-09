package com.iambabul.blog.service;

import com.iambabul.blog.entity.BlogResponse;
import com.iambabul.blog.entity.TitleSubtitle;
import com.iambabul.blog.repository.TitleSubtitleRepository;
import com.iambabul.blog.util.UtilBase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class TitleSubtitleService extends UtilBase {
    private final TitleSubtitleRepository titleSubtitleRepository;

    public List<TitleSubtitle> getAllTitleSubtitle() {
        log.info("getAllTitleSubtitle");
        try {
            return titleSubtitleRepository.findAll();
        }
        catch (Exception ex) {
            log.error(ex.getMessage());
            throw ex;
        }
    }

    public TitleSubtitle getTitleSubtitle(Long id) {
        log.info("getTitleSubtitle");
        try {
            return titleSubtitleRepository.findById(id)
                    .orElseThrow(() -> new IllegalStateException("No found"));
        }
        catch (Exception ex) {
            log.error(ex.getMessage());
            throw ex;
        }
    }

    public BlogResponse postTitleSubtitle(TitleSubtitle titleSubtitle) {
        log.info("postTitleSubtitle");
        BlogResponse response = null;
        try {
            titleSubtitle.collectAndSetCreateUpdateDate();
            titleSubtitleRepository.save(titleSubtitle);
            response = new BlogResponse("success", getText("title.and.subtitle.has.been.saved.successfully"));
            return response;
        }
        catch (Exception ex) {
            log.error(ex.getMessage());
            response = new BlogResponse("failed", getText("x0.failed.to.save.title.and.subtitle-x1", "babul", ex.getMessage()));
            return response;
        }
    }

    public BlogResponse putTitleSubtitle(TitleSubtitle titleSubtitle) {
        log.info("putTitleSubtitle");
        BlogResponse response = null;
        try {
            TitleSubtitle existingTitleSubtitle = titleSubtitleRepository.findById(titleSubtitle.getId())
                    .orElseThrow(() -> new IllegalStateException("No found"));
            if (existingTitleSubtitle != null) {
                titleSubtitleRepository.save(titleSubtitle);
                response = new BlogResponse(getText("success"), getText("title.and.subtitle.has.been.updated.successfully"));
            }
            else {
                response = new BlogResponse(getText("failed"), getText("x0.failed.to.update.title.and.subtitle-x1", "babul", "unknown"));
            }
            return response;
        }
        catch (Exception ex) {
            log.error(ex.getMessage());
            response = new BlogResponse(getText("failed"), getText("x0.failed.to.update.title.and.subtitle-x1", "babul", ex.getMessage()));
            return response;
        }
    }

    public BlogResponse deleteTitleSubtitle(Long id) {
        log.info("deleteTitleSubtitle");
        BlogResponse response = null;
        try {
            if (id != null) {
                titleSubtitleRepository.deleteById(id);
                response = new BlogResponse(getText("success"), getText("title.and.subtitle.has.been.deleted.successfully"));
            }
            else {
                response = new BlogResponse(getText("failed"), getText("x0.failed.to.delete.title.and.subtitle-x1", "babul", "unknown"));
            }
            return response;
        }
        catch (Exception ex) {
            log.error(ex.getMessage());
            response = new BlogResponse(getText("failed"), getText("x0.failed.to.delete.title.and.subtitle-x1", "babul", ex.getMessage()));
            return response;
        }
    }
}
