package com.iambabul.blog.controller;

import com.iambabul.blog.entity.BlogResponse;
import com.iambabul.blog.entity.Content;
import com.iambabul.blog.entity.TitleSubtitle;
import com.iambabul.blog.service.ContentService;
import com.iambabul.blog.service.TitleSubtitleService;
import com.iambabul.blog.util.ApiEndPoint;
import com.iambabul.blog.util.UtilBase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(ApiEndPoint.HOME_ROOT)
public class HomeController extends BaseController {
    private final TitleSubtitleService titleSubtitleService;
    private final ContentService contentService;

    @PostMapping(ApiEndPoint.HOME_TITLE_SUBTITLE)
    public ResponseEntity<BlogResponse> postTitleSubtitle(@RequestBody TitleSubtitle titleSubtitle) {
        log.info("postTitleSubtitle");
        URI location = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path(ApiEndPoint.HOME_ROOT_TITLE_SUBTITLE).toUriString());
        BlogResponse blogResponse = titleSubtitleService.postTitleSubtitle(titleSubtitle);
        return ResponseEntity.created(location).body(blogResponse);
    }

    @GetMapping(ApiEndPoint.HOME_TITLE_SUBTITLE + "/{id}")
    public ResponseEntity<?> getTitleSubtitle(@PathVariable Long id) {
        log.info("getTitleSubtitle");
        try {
            TitleSubtitle titleSubtitle = titleSubtitleService.getTitleSubtitle(id);
            return ResponseEntity.ok(titleSubtitle);
        }
        catch (Exception ex) {
            log.error(ex.getMessage());
            BlogResponse blogResponse = new BlogResponse("failed", getMessage("failed.to.load.title.and.subtitle-x0", ex.getMessage()));
            return ResponseEntity.ok(blogResponse);
        }
    }

    @PutMapping(ApiEndPoint.HOME_TITLE_SUBTITLE)
    public ResponseEntity<BlogResponse> putTitleSubtitle(@RequestBody TitleSubtitle titleSubtitle) {
        log.info("putTitleSubtitle");
        BlogResponse blogResponse = titleSubtitleService.putTitleSubtitle(titleSubtitle);
        return ResponseEntity.ok(blogResponse);
    }

    @DeleteMapping(ApiEndPoint.HOME_TITLE_SUBTITLE + "/{id}")
    public ResponseEntity<BlogResponse> deleteTitleSubtitle(@PathVariable Long id) {
        log.info("deleteTitleSubtitle");
        BlogResponse blogResponse = titleSubtitleService.deleteTitleSubtitle(id);
        return ResponseEntity.ok(blogResponse);
    }

    @PostMapping(ApiEndPoint.HOME_CONTENTS)
    public ResponseEntity<BlogResponse> postContents(@RequestBody Content content) {
        log.info("postContents");
        URI location = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path(ApiEndPoint.HOME_ROOT_CONTENTS).toUriString());
        BlogResponse blogResponse = contentService.postContents(content);
        return ResponseEntity.created(location).body(blogResponse);
    }

    @GetMapping("/contents")
    public ResponseEntity<List<Content>> getContents() {
        List<Content> contents = contentService.getContents();
        return ResponseEntity.ok(contents);
    }
}
