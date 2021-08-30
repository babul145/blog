package com.iambabul.blog.controller;

import com.iambabul.blog.entity.Content;
import com.iambabul.blog.entity.TitleSubtitle;
import com.iambabul.blog.service.ContentService;
import com.iambabul.blog.service.TitleSubtitleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/home")
public class HomeController {

    private final TitleSubtitleService titleSubtitleService;
    private final ContentService contentService;

    @Autowired
    public HomeController(
            TitleSubtitleService titleSubtitleService,
            ContentService contentService
    ) {
        this.titleSubtitleService = titleSubtitleService;
        this.contentService = contentService;
    }

    @GetMapping("/title-subtitle")
    public ResponseEntity<TitleSubtitle> getTitleSubtitle() {
        log.info("getTitleSubtitle");
        TitleSubtitle titleSubtitle = titleSubtitleService.getTitleSubtitle();
        return ResponseEntity.ok(titleSubtitle);
    }

    @GetMapping("/contents")
    public ResponseEntity<List<Content>> getContents() {
        List<Content> contents = contentService.getContents();
        return ResponseEntity.ok(contents);
    }
}
