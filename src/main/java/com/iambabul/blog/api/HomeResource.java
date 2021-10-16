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
    @PostMapping(HOME_CONTENTS)
    public ResponseEntity<BlogResponse> postContents(@RequestBody Content content) {
        log.info("postContents");
        URI location = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path(HOME_ROOT_CONTENTS).toUriString());
        BlogResponse blogResponse = contentService.postContents(content);
        return ResponseEntity.created(location).body(blogResponse);
    }

    @GetMapping(HOME_CONTENTS)
    public ResponseEntity<?> getContents() {
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

    @GetMapping(HOME_CONTENTS + "/{id}")
    public ResponseEntity<?> getContent(@PathVariable Long id) {
        log.info("getContent");
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

    @PutMapping(HOME_CONTENTS)
    public ResponseEntity<BlogResponse> putContent(@RequestBody Content content) {
        log.info("putContent");
        BlogResponse blogResponse = contentService.putContent(content);
        return ResponseEntity.ok(blogResponse);
    }

    @DeleteMapping(HOME_CONTENTS + "/{id}")
    public ResponseEntity<BlogResponse> deleteContent(@PathVariable Long id) {
        log.info("deleteContent");
        BlogResponse blogResponse = contentService.deleteContent(id);
        return ResponseEntity.ok(blogResponse);
    }
    //end content

    //start comment
    @PostMapping(HOME_COMMENT)
    public ResponseEntity<BlogResponse> postComment(@RequestBody Comment comment) {
        log.info("postComment");
        URI location = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path(HOME_ROOT_COMMENT).toUriString());
        BlogResponse blogResponse = commentService.postComment(comment);
        return ResponseEntity.created(location).body(blogResponse);
    }

    @GetMapping(HOME_COMMENT)
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

    @GetMapping(HOME_COMMENT + "/{id}")
    public ResponseEntity<?> getComment(@PathVariable Long id) throws BlogException {
        log.info("getComment");
        Comment comment = commentService.getComment(id);
        return ResponseEntity.ok(comment);
    }

    @PutMapping(HOME_COMMENT)
    public ResponseEntity<BlogResponse> putComment(@RequestBody Comment comment) {
        log.info("putComment");
        BlogResponse blogResponse = commentService.putComment(comment);
        return ResponseEntity.ok(blogResponse);
    }

    @DeleteMapping(HOME_COMMENT + "/{id}")
    public ResponseEntity<BlogResponse> deleteComment(@PathVariable Long id) {
        log.info("deleteComment");
        BlogResponse blogResponse = commentService.deleteComment(id);
        return ResponseEntity.ok(blogResponse);
    }
    //end comment

    /*@GetMapping(HOME_5CONTENTS)
    public ResponseEntity<?> get5Contents() {
        try {
            Page<Content> contents = contentService.get5Contents();
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
    }*/

    @GetMapping(HOME_5CONTENTS)
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
