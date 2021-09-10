package com.iambabul.blog.service;

import com.iambabul.blog.entity.BlogResponse;
import com.iambabul.blog.entity.Comment;
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
        log.info("postComment");
        BlogResponse blogResponse = null;
        try {
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
            blogResponse = getBlogResponse(Constants.RESPONSE_TYPE_FAILED, Comment.class.getSimpleName(), ex.getMessage());
            return blogResponse;
        }
    }

    public List<Comment> getComments() {
        log.info("getComments");
        try {
            return commentRepository.findAll();
        }
        catch (Exception ex) {
            log.error(ex.getMessage());
            throw ex;
        }
    }

    public Comment getComment(Long id) {
        log.info("getComment");
        try {
            return commentRepository.findById(id)
                    .orElseThrow(() -> new IllegalStateException(getText("no.found")));
        }
        catch (Exception ex) {
            log.error(ex.getMessage());
            throw ex;
        }
    }

    public BlogResponse putComment(Comment comment) {
        log.info("putComment");
        BlogResponse response = null;
        try {
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
            response = new BlogResponse(
                    getText("failed"),
                    getText("failed.to.update.x0-x1", getText("comment"), ex.getMessage())
            );
            return response;
        }
    }

    public BlogResponse deleteComment(Long id) {
        log.info("deleteComment");
        BlogResponse response = null;
        try {
            if (id != null) {
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
            response = new BlogResponse(
                    getText("failed"),
                    getText("failed.to.delete.x0-x1", getText("comment"), ex.getMessage())
            );
            return response;
        }
    }
}
