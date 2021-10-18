package com.iambabul.blog.service;

import com.iambabul.blog.entity.BlogResponse;
import com.iambabul.blog.entity.TitleSubtitle;
import com.iambabul.blog.exception.BlogException;
import com.iambabul.blog.repository.TitleSubtitleRepository;
import com.iambabul.blog.util.UtilBase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class TitleSubtitleService extends UtilBase {
    private final TitleSubtitleRepository titleSubtitleRepository;

    public List<TitleSubtitle> getAllTitleSubtitle() {
        try {
            log.info("Fetching all titles & subtitles");
            return titleSubtitleRepository.findAll();
        }
        catch (Exception ex) {
            log.error(ex.getMessage());
            throw new BlogException(getText("failed.to.load.title.and.subtitle-x0", ex.getMessage()));
        }
    }

    public TitleSubtitle getTitleSubtitle(Long id) {
        log.info("getTitleSubtitle");
        try {
            log.info("Fetching title & subtitle {}", id);
            return titleSubtitleRepository.findById(id)
                    .orElseThrow(() -> new IllegalStateException("No found"));
        }
        catch (Exception ex) {
            log.error(ex.getMessage());
            throw new BlogException(getText("failed.to.load.title.and.subtitle-x0", ex.getMessage()), NOT_FOUND.value());
        }
    }

    public BlogResponse postTitleSubtitle(TitleSubtitle titleSubtitle) {
        BlogResponse response = null;
        try {
            log.info("Saving title & subtitle in the database");
            titleSubtitle.collectAndSetCreateUpdateDate();
            titleSubtitleRepository.save(titleSubtitle);
            response = new BlogResponse("success", getText("title.and.subtitle.has.been.saved.successfully"));
            return response;
        }
        catch (Exception ex) {
            log.error(ex.getMessage());
            throw new BlogException(getText("failed.to.save.title.and.subtitle-x0", ex.getMessage()));
        }
    }

    public BlogResponse putTitleSubtitle(TitleSubtitle titleSubtitle) {
        BlogResponse response = null;
        try {
            log.info("Updating title & subtitle {}", titleSubtitle.getTitle());
            TitleSubtitle existingTitleSubtitle = titleSubtitleRepository.findById(titleSubtitle.getId())
                    .orElseThrow(() -> new IllegalStateException("No found"));
            if (existingTitleSubtitle != null) {
                titleSubtitle.collectCreateDateAndSetUpdateDate(existingTitleSubtitle.getCreated());
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
            throw new BlogException(getText("failed.to.update.title.and.subtitle-x0", ex.getMessage()));
        }
    }

    public BlogResponse deleteTitleSubtitle(Long id) {
        BlogResponse response = null;
        try {
            if (id != null) {
                log.info("Deleting title & subtitle {}", id);
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
            throw new BlogException(getText("failed.to.delete.title.and.subtitle-x0", ex.getMessage()));
        }
    }
}
