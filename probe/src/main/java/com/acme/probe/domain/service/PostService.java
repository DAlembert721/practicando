package com.acme.probe.domain.service;

import com.acme.probe.domain.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


public interface PostService {
    Page<Post> getAllPosts(Pageable pageable);
    Post getPostById(Long postId);
    Post createPost(Post post);
    Post updatePost(Long postId, Post post);
    ResponseEntity<?> deletePost(Long postId);
    Post assignPostTag(Long postId, Long tagId);
    Post unAssignPostTag(Long postId, Long tagId);
    Page<Post> getAllPostsByTagId(Long tagId, Pageable pageable);

}
