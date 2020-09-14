package com.acme.probe.controller;

import com.acme.probe.domain.model.Post;
import com.acme.probe.domain.repository.PostRepository;
import com.acme.probe.domain.service.PostService;
import com.acme.probe.exception.ResourceNotFoundException;
import com.acme.probe.resource.PostResource;
import com.acme.probe.resource.SavePostResource;
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
public class PostsController {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private PostService postService;


    /*@Autowired
    public PostsController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }*/

    @GetMapping("/posts")
    public Page<PostResource> getAllPosts(Pageable pageable) {
        Page<Post> postsPage = postService.getAllPosts(pageable);
        List<PostResource> resources = postsPage.getContent()
                .stream()
                .map(this::convertToResource)
                .collect(Collectors.toList());
        return new PageImpl<>(resources,pageable, resources.size());
    }

    @PostMapping("/posts")
    public PostResource createPost(@Valid @RequestBody SavePostResource resource) {
        Post post = convertToEntity(resource);
        return convertToResource(postService.createPost(post));
    }

    @PutMapping("/posts/{postId}")
    public PostResource updatePost(@PathVariable Long postId, @Valid @RequestBody SavePostResource postRequest) {
        Post post = convertToEntity(postRequest);
        return convertToResource(postService.updatePost(postId, post));
    }

    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable Long postId) {
        return postService.deletePost(postId);
    }

    @GetMapping("/tags/{tagId}/posts")
    public Page<PostResource> getAllPostsByTagId(
            @PathVariable(name = "tagId") Long tagId,
            Pageable pageable) {
        Page<Post> postsPage = postService.getAllPostsByTagId(tagId,pageable);
        List<PostResource> resources = postsPage.getContent()
                .stream()
                .map(this::convertToResource)
                .collect(Collectors.toList());
        return new PageImpl<>(resources,pageable,resources.size());
    }

    @PostMapping("/posts/{postId}/tags/{tagId}")
    public PostResource assignPostTag(
            @PathVariable(name = "postId") Long postId,
            @PathVariable(name = "tagId") Long tagId
    ) {
        return convertToResource(postService.assignPostTag(postId,tagId));
    }

    @PutMapping("/posts/{postId}/tags/{tagId}")
    public PostResource unAssignPostTag(
            @PathVariable(name = "postId") Long postId,
            @PathVariable(name = "tagId") Long tagId
    ) {
        return convertToResource(postService.unAssignPostTag(postId,tagId));
    }

    private Post convertToEntity(SavePostResource resource) {
        return mapper.map(resource, Post.class);
    }

    private PostResource convertToResource(Post entity) {
        return mapper.map(entity, PostResource.class);
    }
}
