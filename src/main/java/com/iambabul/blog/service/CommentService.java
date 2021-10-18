package com.iambabul.blog.service;

import com.iambabul.blog.entity.BlogResponse;
import com.iambabul.blog.entity.Comment;
import com.iambabul.blog.exception.BlogException;
import com.iambabul.blog.repository.CommentRepository;
import com.iambabul.blog.util.Constants;
import com.iambabul.blog.util.UtilBase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CommentService extends UtilBase {
    private final CommentRepository commentRepository;

    public BlogResponse postComment(Comment comment) {
        BlogResponse blogResponse = null;
        try {
            log.info("Saving comment {} to the database", comment.getText());
            comment.collectAndSetCreateUpdateDate();
            commentRepository.save(comment);
            blogResponse = new BlogResponse(
                    getText("success"),
                    getText("x0.has.been.added.successfully", getText("comment"))
            );
            return blogResponse;
        }
        catch (Exception ex) {
            log.error(ex.getMessage());
            throw new BlogException(getText("failed.to.save.comment-x0", ex.getMessage()));
        }
    }

    public List<Comment> getComments() {
        try {
            log.info("Fetching all comments");
            return commentRepository.findAll();
        }
        catch (Exception ex) {
            log.error(ex.getMessage());
            throw new BlogException(getText("failed.to.load.comment-x0", ex.getMessage()));
        }
    }

    public Comment getComment(Long id) {
        try {
            log.info("Fetching comment {}", id);
            return commentRepository.findById(id).get();
        }
        catch (Exception ex) {
            log.error(ex.getMessage());
            throw new BlogException(getText("failed.to.load.comment-x0", ex.getMessage()));
        }
    }

    public BlogResponse putComment(Comment comment) {
        BlogResponse response = null;
        try {
            log.info("Updating comment {}", comment.getId());
            Comment existingContent = commentRepository.findById(comment.getId())
                    .orElseThrow(() -> new IllegalStateException(getText("no.found")));
            if (existingContent != null) {
                comment.collectCreateDateAndSetUpdateDate(existingContent.getCreated());
                commentRepository.save(comment);
                response = new BlogResponse(
                        getText("success"),
                        getText("x0.has.been.updated.successfully", getText("comment"))
                );
            }
            else {
                response = new BlogResponse(
                        getText("failed"),
                        getText("failed.to.update.x0-x1",getText("comment"), getText("no.found"))
                );
            }
            return response;
        }
        catch (Exception ex) {
            log.error(ex.getMessage());
            throw new BlogException(getText("failed.to.update.comment-x0", ex.getMessage()));
        }
    }

    public BlogResponse deleteComment(Long id) {
        BlogResponse response = null;
        try {
            if (id != null) {
                log.info("Deleting comment {}", id);
                commentRepository.deleteById(id);
                response = new BlogResponse(
                        getText("success"),
                        getText("x0.has.been.deleted.successfully", getText("comment"))
                );
            }
            else {
                response = new BlogResponse(
                        getText("failed"),
                        getText("failed.to.delete.x0-x1", getText("no.found"))
                );
            }
            return response;
        }
        catch (Exception ex) {
            log.error(ex.getMessage());
            throw new BlogException(getText("failed.to.delete.comment-x0", ex.getMessage()));
        }
    }
}
