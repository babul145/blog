package com.iambabul.blog.controller;

import com.iambabul.blog.entity.BlogResponse;
import com.iambabul.blog.entity.Content;
import com.iambabul.blog.entity.TitleSubtitle;
import com.iambabul.blog.service.ContentService;
import com.iambabul.blog.service.TitleSubtitleService;
import com.iambabul.blog.util.ApiEndPoint;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(ApiEndPoint.HOME_ROOT)
public class HomeController {
    private final TitleSubtitleService titleSubtitleService;
    private final ContentService contentService;

    @PostMapping(ApiEndPoint.HOME_TITLE_SUBTITLE)
    public ResponseEntity<BlogResponse> postTitleSubtitle(@RequestBody TitleSubtitle titleSubtitle) {
        log.info("postTitleSubtitle");
        URI location = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path(ApiEndPoint.HOME_ROOT_TITLE_SUBTITLE).toUriString());
        BlogResponse blogResponse = titleSubtitleService.postTitleSubtitle(titleSubtitle);
        return ResponseEntity.created(location).body(blogResponse);
    }

    @GetMapping(ApiEndPoint.HOME_TITLE_SUBTITLE)
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
