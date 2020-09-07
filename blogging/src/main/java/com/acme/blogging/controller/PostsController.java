package com.acme.blogging.controller;


import com.acme.blogging.domain.model.Post;
import com.acme.blogging.domain.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostsController {
    @Autowired
    private PostRepository postRepository;

    @GetMapping("/posts")
    public Page<Post> getAllPosts(Pageable pageable) {

        return postRepository.findAll(pageable);
    }




}
