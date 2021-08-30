package com.iambabul.blog.service;

import com.iambabul.blog.entity.BlogResponse;
import com.iambabul.blog.entity.TitleSubtitle;
import com.iambabul.blog.repository.TitleSubtitleRepository;
import com.iambabul.blog.util.Constants;
import com.sun.deploy.net.HttpResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class TitleSubtitleService {
    private final TitleSubtitleRepository titleSubtitleRepository;
    private final Constants constants;

    public TitleSubtitle getTitleSubtitle() {
        log.info("getTitleSubtitle");
        try {
            return titleSubtitleRepository.findAll().get(Constants.FIRST_INDEX);
        }
        catch (Exception ex) {
            log.error(ex.getMessage());
            return null;
        }
    }

    public BlogResponse postTitleSubtitle(TitleSubtitle titleSubtitle) {
        log.info("postTitleSubtitle");
        BlogResponse response = null;
        try {
            titleSubtitleRepository.save(titleSubtitle);
            response = new BlogResponse("success", constants.getMessage("title.and.subtitle.has.been.saved.successfully"));
            return response;
        }
        catch (Exception ex) {
            log.error(ex.getMessage());
            response = new BlogResponse("failed", constants.getMessage("x0.failed.to.save.title.and.subtitle-x1", "babul", ex.getMessage()));
            return response;
        }
    }
}
