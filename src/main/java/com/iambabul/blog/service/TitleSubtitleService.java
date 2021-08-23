package com.iambabul.blog.service;

import com.iambabul.blog.entity.TitleSubtitle;
import com.iambabul.blog.repository.TitleSubtitleRepository;
import com.iambabul.blog.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TitleSubtitleService {

    private final TitleSubtitleRepository titleSubtitleRepository;

    @Autowired
    public TitleSubtitleService(TitleSubtitleRepository titleSubtitleRepository) {
        this.titleSubtitleRepository = titleSubtitleRepository;
    }

    public TitleSubtitle getTitleSubtitle() {
        return titleSubtitleRepository.findAll().get(Constants.FIRST_INDEX);
    }
}
