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
import java.util.Date;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(ApiEndPoint.HOME_ROOT)
public class HomeController extends BaseController {
    private final TitleSubtitleService titleSubtitleService;
    private final ContentService contentService;

    //start title subtitle
    @PostMapping(ApiEndPoint.HOME_TITLE_SUBTITLE)
    public ResponseEntity<BlogResponse> postTitleSubtitle(@RequestBody TitleSubtitle titleSubtitle) {
        log.info("postTitleSubtitle");
        URI location = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path(ApiEndPoint.HOME_ROOT_TITLE_SUBTITLE).toUriString());
        BlogResponse blogResponse = titleSubtitleService.postTitleSubtitle(titleSubtitle);
        return ResponseEntity.created(location).body(blogResponse);
    }

    @GetMapping(ApiEndPoint.HOME_TITLE_SUBTITLE)
    public ResponseEntity<?> getAllTitleSubtitle() {
        log.info("getAllTitleSubtitle");
        try {
            List<TitleSubtitle> titleSubtitles = titleSubtitleService.getAllTitleSubtitle();
            return ResponseEntity.ok(titleSubtitles);
        }
        catch (Exception ex) {
            log.error(ex.getMessage());
            BlogResponse blogResponse = new BlogResponse("failed", getMessage("failed.to.load.title.and.subtitle-x0", ex.getMessage()));
            return ResponseEntity.ok(blogResponse);
        }
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
        titleSubtitle.setUpdated(new Date());
        BlogResponse blogResponse = titleSubtitleService.putTitleSubtitle(titleSubtitle);
        return ResponseEntity.ok(blogResponse);
    }

    @DeleteMapping(ApiEndPoint.HOME_TITLE_SUBTITLE + "/{id}")
    public ResponseEntity<BlogResponse> deleteTitleSubtitle(@PathVariable Long id) {
        log.info("deleteTitleSubtitle");
        BlogResponse blogResponse = titleSubtitleService.deleteTitleSubtitle(id);
        return ResponseEntity.ok(blogResponse);
    }
    //end title subtitle

    //start content
    @PostMapping(ApiEndPoint.HOME_CONTENTS)
    public ResponseEntity<BlogResponse> postContents(@RequestBody Content content) {
        log.info("postContents");
        URI location = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path(ApiEndPoint.HOME_ROOT_CONTENTS).toUriString());
        BlogResponse blogResponse = contentService.postContents(content);
        return ResponseEntity.created(location).body(blogResponse);
    }

    @GetMapping(ApiEndPoint.HOME_CONTENTS)
    public ResponseEntity<?> getContents() {
        try {
            List<Content> contents = contentService.getContents();
            return ResponseEntity.ok(contents);
        }
        catch (Exception ex) {
            log.error(ex.getMessage());
            BlogResponse blogResponse = new BlogResponse("failed", getMessage("failed.to.load.x0-x1", getMessage("content"), ex.getMessage()));
            return ResponseEntity.ok(blogResponse);
        }
    }

    @GetMapping(ApiEndPoint.HOME_CONTENTS + "/{id}")
    public ResponseEntity<?> getContent(@PathVariable Long id) {
        log.info("getContent");
        try {
            Content content = contentService.getContent(id);
            return ResponseEntity.ok(content);
        }
        catch (Exception ex) {
            log.error(ex.getMessage());
            BlogResponse blogResponse = new BlogResponse("failed", getMessage("failed.to.load.x0-x1", Content.class.getSimpleName(), ex.getMessage()));
            return ResponseEntity.ok(blogResponse);
        }
    }

    @PutMapping(ApiEndPoint.HOME_CONTENTS)
    public ResponseEntity<BlogResponse> putContent(@RequestBody Content content) {
        log.info("putContent");
        BlogResponse blogResponse = contentService.putContent(content);
        return ResponseEntity.ok(blogResponse);
    }

    @DeleteMapping(ApiEndPoint.HOME_CONTENTS + "/{id}")
    public ResponseEntity<BlogResponse> deleteContent(@PathVariable Long id) {
        log.info("deleteContent");
        BlogResponse blogResponse = contentService.deleteContent(id);
        return ResponseEntity.ok(blogResponse);
    }
    //end content
}
