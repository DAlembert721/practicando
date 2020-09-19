package com.acme.probe.controller;

import com.acme.probe.domain.model.Comment;
import com.acme.probe.domain.model.Post;
import com.acme.probe.domain.repository.CommentRepository;
import com.acme.probe.domain.repository.PostRepository;
import com.acme.probe.domain.service.CommentService;
import com.acme.probe.exception.ResourceNotFoundException;
import com.acme.probe.resource.CommentResource;
import com.acme.probe.resource.PostResource;
import com.acme.probe.resource.SaveCommentResource;
import com.acme.probe.resource.SavePostResource;
import org.apache.catalina.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class CommentsController {

    @Autowired
    private ModelMapper mapper;


    @Autowired
    private CommentService commentService;

    @GetMapping("/posts/{postId}/comments")
    public Page<CommentResource> getAllCommentsByPostId(
            @PathVariable Long postId, Pageable pageable) {
        Page<Comment> commentPage = commentService.getAllCommentsByPostId(postId, pageable);
        List<CommentResource> resources = commentPage.getContent()
                .stream()
                .map(this::convertToResource)
                .collect(Collectors.toList());
        return new PageImpl<>(resources, pageable, resources.size());

    }

    @PostMapping("/posts/{postId}/comments")
    public CommentResource createComment(@PathVariable(name = "postId") Long postId, @Valid @RequestBody SaveCommentResource resource) {
        Comment comment = convertToEntity(resource);
        return convertToResource(commentService.createComment(postId, comment));
    }

    @PutMapping("/posts/{postId}/comments/{commentId}")
    public CommentResource updateComment(
            @PathVariable(value = "postId") Long postId,
            @PathVariable(value = "commentId") Long commentId,
            @Valid @RequestBody SaveCommentResource commentRequest) {
        return convertToResource(commentService.updateComment(postId, commentId, convertToEntity(commentRequest)));
    }

    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<?> deleteComment(
            @PathVariable(value = "postId") Long postId,
            @PathVariable(value = "commentId") Long commentId) {
        return commentService.deleteComment(postId, commentId);
    }


    private Comment convertToEntity(SaveCommentResource resource) {
        return mapper.map(resource, Comment.class);
    }

    private CommentResource convertToResource(Comment entity) {
        return mapper.map(entity, CommentResource.class);
    }

}
