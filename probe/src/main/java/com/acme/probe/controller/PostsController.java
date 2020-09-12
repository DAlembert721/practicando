package com.acme.probe.controller;

import com.acme.probe.domain.model.Post;
import com.acme.probe.domain.repository.PostRepository;
import com.acme.probe.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/api")
public class PostsController {

    @Autowired
    private  PostRepository postRepository;

    /*@Autowired
    public PostsController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }*/

    @GetMapping("/posts")
    public Page<Post> getAllPosts(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    @PostMapping("/posts")
    public Post createPost(@Valid @RequestBody Post post) {
        return postRepository.save(post);
    }

    @PutMapping("/posts/{postId}")
    public Post updatePost(@PathVariable Long postId, @Valid @RequestBody Post postRequest) {
        return postRepository.findById(postId)
                .map(post -> {
                    post.setTitle(postRequest.getTitle());
                    post.setDescription(postRequest.getDescription());
                    post.setContent(postRequest.getContent());
                    return  postRepository.save(post);
                }).orElseThrow(() -> new ResourceNotFoundException(
                        "postId " + postId + " not found"));
    }

    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable Long postId) {
        return  postRepository.findById(postId)
                .map(post -> {
                    postRepository.delete(post);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException(
                        "postId " + postId + " not found"));
    }

}
