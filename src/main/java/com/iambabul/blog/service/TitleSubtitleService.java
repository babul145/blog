package com.iambabul.blog.service;

import com.iambabul.blog.entity.TitleSubtitle;
import com.iambabul.blog.repository.TitleSubtitleRepository;
import com.iambabul.blog.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service @Slf4j @Transactional
public class TitleSubtitleService {

    private final TitleSubtitleRepository titleSubtitleRepository;

    @Autowired
    public TitleSubtitleService(TitleSubtitleRepository titleSubtitleRepository) {
        this.titleSubtitleRepository = titleSubtitleRepository;
    }

    public TitleSubtitle getTitleSubtitle() {
        try {
            log.info("getTitleSubtitle");
            return titleSubtitleRepository.findAll().get(Constants.FIRST_INDEX);
        }
        catch (Exception ex) {
            log.error(ex.getMessage());
            return null;
        }
    }
}
