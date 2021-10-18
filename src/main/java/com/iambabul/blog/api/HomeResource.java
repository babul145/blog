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
        log.info("Saving title & subtitle in the database");
        URI location = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/home/title-subtitle").toUriString());
        BlogResponse blogResponse = titleSubtitleService.postTitleSubtitle(titleSubtitle);
        return ResponseEntity.created(location).body(blogResponse);
    }

    @GetMapping("/title-subtitle")
    public ResponseEntity<?> getAllTitleSubtitle() {
        log.info("Fetching all titles & subtitles");
        try {
            List<TitleSubtitle> titleSubtitles = titleSubtitleService.getAllTitleSubtitle();
            return ResponseEntity.ok(titleSubtitles);
        }
        catch (Exception ex) {
            log.error(ex.getMessage());
            BlogResponse blogResponse = new BlogResponse("failed", getText("failed.to.load.title.and.subtitle-x0", ex.getMessage()));
            return ResponseEntity.ok(blogResponse);
        }
    }

    @GetMapping("/title-subtitle/{id}")
    public ResponseEntity<?> getTitleSubtitle(@PathVariable Long id) {
        log.info("Fetching title & subtitle {}", id);
        try {
            TitleSubtitle titleSubtitle = titleSubtitleService.getTitleSubtitle(id);
            return ResponseEntity.ok(titleSubtitle);
        }
        catch (Exception ex) {
            log.error(ex.getMessage());
            BlogResponse blogResponse = new BlogResponse("failed", getText("failed.to.load.title.and.subtitle-x0", ex.getMessage()));
            return ResponseEntity.ok(blogResponse);
        }
    }

    @PutMapping("/title-subtitle")
    public ResponseEntity<BlogResponse> putTitleSubtitle(@RequestBody TitleSubtitle titleSubtitle) {
        log.info("Updating title & subtitle {}", titleSubtitle.getTitle());
        titleSubtitle.setUpdated(new Date());
        BlogResponse blogResponse = titleSubtitleService.putTitleSubtitle(titleSubtitle);
        return ResponseEntity.ok(blogResponse);
    }

    @DeleteMapping("/title-subtitle/{id}")
    public ResponseEntity<BlogResponse> deleteTitleSubtitle(@PathVariable Long id) {
        log.info("Deleting title & subtitle {}", id);
        BlogResponse blogResponse = titleSubtitleService.deleteTitleSubtitle(id);
        return ResponseEntity.ok(blogResponse);
    }
    //end title subtitle

    //start content
    @PostMapping("/content")
    public ResponseEntity<BlogResponse> postContents(@RequestBody Content content) {
        log.info("Saving content {} to the database", content.getId());
        URI location = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/home/content").toUriString());
        BlogResponse blogResponse = contentService.postContents(content);
        return ResponseEntity.created(location).body(blogResponse);
    }

    @GetMapping("/content")
    public ResponseEntity<?> getContents() {
        log.info("Fetching all contents");
        try {
            List<Content> contents = contentService.getContents();
            return ResponseEntity.ok(contents);
        }
        catch (Exception ex) {
            log.error(ex.getMessage());
            BlogResponse blogResponse = new BlogResponse("failed", getText("failed.to.load.x0-x1", getText("content"), ex.getMessage()));
            return ResponseEntity.ok(blogResponse);
        }
    }

    @GetMapping("/content/{id}")
    public ResponseEntity<?> getContent(@PathVariable Long id) {
        log.info("Fetching content {}", id);
        try {
            Content content = contentService.getContent(id);
            return ResponseEntity.ok(content);
        }
        catch (Exception ex) {
            log.error(ex.getMessage());
            BlogResponse blogResponse = new BlogResponse("failed", getText("failed.to.load.x0-x1", Content.class.getSimpleName(), ex.getMessage()));
            return ResponseEntity.ok(blogResponse);
        }
    }

    @PutMapping("/content")
    public ResponseEntity<BlogResponse> putContent(@RequestBody Content content) {
        log.info("Updating content {}", content.getId());
        BlogResponse blogResponse = contentService.putContent(content);
        return ResponseEntity.ok(blogResponse);
    }

    @DeleteMapping("/content/{id}")
    public ResponseEntity<BlogResponse> deleteContent(@PathVariable Long id) {
        log.info("Deleting content {}", id);
        BlogResponse blogResponse = contentService.deleteContent(id);
        return ResponseEntity.ok(blogResponse);
    }
    //end content

    //start comment
    @PostMapping("/comment")
    public ResponseEntity<BlogResponse> postComment(@RequestBody Comment comment) {
        log.info("Saving comment {} to the database", comment.getText());
        URI location = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/home/content").toUriString());
        BlogResponse blogResponse = commentService.postComment(comment);
        return ResponseEntity.created(location).body(blogResponse);
    }

    @GetMapping("/comment")
    public ResponseEntity<?> getComments() {
        log.info("Fetching all comments");
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
        log.info("Fetching comment {}", id);
        Comment comment = commentService.getComment(id);
        return ResponseEntity.ok(comment);
    }

    @PutMapping("/comment")
    public ResponseEntity<BlogResponse> putComment(@RequestBody Comment comment) {
        log.info("Updating comment {}", comment.getId());
        BlogResponse blogResponse = commentService.putComment(comment);
        return ResponseEntity.ok(blogResponse);
    }

    @DeleteMapping("/comment/{id}")
    public ResponseEntity<BlogResponse> deleteComment(@PathVariable Long id) {
        log.info("Deleting comment {}", id);
        BlogResponse blogResponse = commentService.deleteComment(id);
        return ResponseEntity.ok(blogResponse);
    }
    //end comment

    @GetMapping("/5content")
    public ResponseEntity<?> get5Contents() {
        log.info("Fetching 5 contents");
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
