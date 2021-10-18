package com.iambabul.blog.api;

import com.iambabul.blog.entity.BlogResponse;
import com.iambabul.blog.entity.Comment;
import com.iambabul.blog.entity.Content;
import com.iambabul.blog.entity.TitleSubtitle;
import com.iambabul.blog.exception.BlogException;
import com.iambabul.blog.service.CommentService;
import com.iambabul.blog.service.ContentService;
import com.iambabul.blog.service.TitleSubtitleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Date;
import java.util.List;

import static com.iambabul.blog.util.ApiEndPoint.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/home")
public class HomeResource extends ResourceBase {
    private final TitleSubtitleService titleSubtitleService;
    private final ContentService contentService;
    private final CommentService commentService;

    //start title subtitle
    @PostMapping("/title-subtitle")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<BlogResponse> postTitleSubtitle(@RequestBody TitleSubtitle titleSubtitle) {
        URI location = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/home/title-subtitle").toUriString());
        BlogResponse blogResponse = titleSubtitleService.postTitleSubtitle(titleSubtitle);
        return ResponseEntity.created(location).body(blogResponse);
    }

    @GetMapping("/title-subtitle")
    public ResponseEntity<?> getAllTitleSubtitle() {
        List<TitleSubtitle> titleSubtitles = titleSubtitleService.getAllTitleSubtitle();
        return ResponseEntity.ok(titleSubtitles);
    }

    @GetMapping("/title-subtitle/{id}")
    public ResponseEntity<?> getTitleSubtitle(@PathVariable Long id) {
        TitleSubtitle titleSubtitle = titleSubtitleService.getTitleSubtitle(id);
        return ResponseEntity.ok(titleSubtitle);
    }

    @PutMapping("/title-subtitle")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<BlogResponse> putTitleSubtitle(@RequestBody TitleSubtitle titleSubtitle) {
        titleSubtitle.setUpdated(new Date());
        BlogResponse blogResponse = titleSubtitleService.putTitleSubtitle(titleSubtitle);
        return ResponseEntity.ok(blogResponse);
    }

    @DeleteMapping("/title-subtitle/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<BlogResponse> deleteTitleSubtitle(@PathVariable Long id) {
        BlogResponse blogResponse = titleSubtitleService.deleteTitleSubtitle(id);
        return ResponseEntity.ok(blogResponse);
    }
    //end title subtitle

    //start content
    @PostMapping("/content")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<BlogResponse> postContents(@RequestBody Content content) {
        URI location = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/home/content").toUriString());
        BlogResponse blogResponse = contentService.postContents(content);
        return ResponseEntity.created(location).body(blogResponse);
    }

    @GetMapping("/content")
    public ResponseEntity<?> getContents() {
        List<Content> contents = contentService.getContents();
        return ResponseEntity.ok(contents);
    }

    @GetMapping("/content/{id}")
    public ResponseEntity<?> getContent(@PathVariable Long id) {
        Content content = contentService.getContent(id);
        return ResponseEntity.ok(content);
    }

    @PutMapping("/content")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<BlogResponse> putContent(@RequestBody Content content) {
        BlogResponse blogResponse = contentService.putContent(content);
        return ResponseEntity.ok(blogResponse);
    }

    @DeleteMapping("/content/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<BlogResponse> deleteContent(@PathVariable Long id) {
        BlogResponse blogResponse = contentService.deleteContent(id);
        return ResponseEntity.ok(blogResponse);
    }
    //end content

    //start comment
    @PostMapping("/comment")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<BlogResponse> postComment(@RequestBody Comment comment) {
        URI location = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/home/content").toUriString());
        BlogResponse blogResponse = commentService.postComment(comment);
        return ResponseEntity.created(location).body(blogResponse);
    }

    @GetMapping("/comment")
    public ResponseEntity<?> getComments() {
        try {
            List<Comment> comments = commentService.getComments();
            return ResponseEntity.ok(comments);
        }
        catch (Exception ex) {
            log.error(ex.getMessage());
            BlogResponse blogResponse = new BlogResponse(
                    getText("failed"),
                    getText("failed.to.load.x0-x1", getText("comment"), ex.getMessage())
            );
            return ResponseEntity.ok(blogResponse);
        }
    }

    @GetMapping("/comment/{id}")
    public ResponseEntity<?> getComment(@PathVariable Long id) throws BlogException {
        Comment comment = commentService.getComment(id);
        return ResponseEntity.ok(comment);
    }

    @PutMapping("/comment")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<BlogResponse> putComment(@RequestBody Comment comment) {
        BlogResponse blogResponse = commentService.putComment(comment);
        return ResponseEntity.ok(blogResponse);
    }

    @DeleteMapping("/comment/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<BlogResponse> deleteComment(@PathVariable Long id) {
        BlogResponse blogResponse = commentService.deleteComment(id);
        return ResponseEntity.ok(blogResponse);
    }
    //end comment

    @GetMapping("/5content")
    public ResponseEntity<?> get5Contents() {
        try {
            List contents = contentService.get5Contents();
            return ResponseEntity.ok().body(contents);
        }
        catch (Exception ex) {
            log.error(ex.getMessage());
            BlogResponse blogResponse = new BlogResponse(
                    getText("failed"),
                    getText("failed.to.load.x0-x1", getText("content"), ex.getMessage())
            );
            return ResponseEntity.ok(blogResponse);
        }
    }
}
