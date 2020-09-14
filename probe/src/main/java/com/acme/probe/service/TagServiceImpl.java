package com.acme.probe.service;

import com.acme.probe.domain.model.Post;
import com.acme.probe.domain.model.Tag;
import com.acme.probe.domain.repository.PostRepository;
import com.acme.probe.domain.repository.TagRepository;
import com.acme.probe.domain.service.TagService;
import com.acme.probe.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private PostRepository postRepository;

    @Override
    public Page<Tag> getAllTags(Pageable pageable) {
        return tagRepository.findAll(pageable);
    }

    @Override
    public Page<Tag> getAllTagsByPostId(Long postId, Pageable pageable) {
        return postRepository.findById(postId)
                .map(post -> {
                    List<Tag> tags = post.getTags();
                    int tagsCount = tags.size();
                    return new PageImpl<>(tags, pageable, tagsCount);
                }).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
    }

    @Override
    public Tag getTagById(Long tagId) {
        return tagRepository.findById(tagId)
                .orElseThrow(() -> new ResourceNotFoundException("Tag", "Id", tagId));
    }

    @Override
    public Tag createTag(Tag tag) {
        return tagRepository.save(tag);
    }

    @Override
    public Tag updateTag(Long tagId, Tag tagDetails) {
        return tagRepository.findById(tagId)
                .map(tag -> {
                    tag.setName(tagDetails.getName());
                    return tagRepository.save(tag);
                }).orElseThrow(() -> new ResourceNotFoundException("Tag", "Id", tagId));
    }

    @Override
    public ResponseEntity<?> deleteTag(Long tagId) {
        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new ResourceNotFoundException("Tag","Id",tagId));
        tagRepository.delete(tag);
        return ResponseEntity.ok().build();


    }
}
