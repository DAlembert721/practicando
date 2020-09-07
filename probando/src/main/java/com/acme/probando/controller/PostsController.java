package com.acme.probando.controller;


import com.acme.probando.model.Post;
import com.acme.probando.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PostsController {
    private final PostRepository postRepository;

    @Autowired
    public PostsController(PostRepository postRepository){
        this.postRepository = postRepository;
    }

    @GetMapping("/posts")
    public List<Post> getAllPosts() {

        return postRepository.findAll();
    }




}
