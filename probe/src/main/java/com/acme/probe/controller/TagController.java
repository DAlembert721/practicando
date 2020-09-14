package com.acme.probe.controller;

import com.acme.probe.domain.model.Tag;
import com.acme.probe.domain.service.TagService;
import com.acme.probe.resource.SaveTagResource;
import com.acme.probe.resource.TagResource;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class TagController {
    @Autowired
    private ModelMapper mapper;

    @Autowired
    private TagService tagService;

    @GetMapping("/posts/{postId}/tags")
    public Page<TagResource> getAllTagsByPostId(
            @PathVariable(name = "postId") Long postId,
            Pageable pageable) {
        List<TagResource> tags = tagService.getAllTagsByPostId(postId,pageable)
                .stream()
                .map(this::convertToResource)
                .collect(Collectors.toList());
        return new PageImpl<>(tags,pageable, tags.size());
    }

    @GetMapping("/tags")
    public Page<TagResource> getAllTags(Pageable pageable) {
        List<TagResource> tags = tagService.getAllTags(pageable)
                .stream()
                .map(this::convertToResource)
                .collect(Collectors.toList());
        return new PageImpl<>(tags,pageable, tags.size());
    }

    @GetMapping("/tags/{tagId}")
    public TagResource getTagById(
            @PathVariable(name = "tagId") Long tagId) {
        return convertToResource(tagService.getTagById(tagId));
    }

    @PostMapping("/tags")
    public TagResource getTagById(@Valid @RequestBody SaveTagResource tagRequest) {
        return convertToResource(tagService.createTag(convertToEntity(tagRequest)));
    }

    @PutMapping("/tags/{tagId}")
    public TagResource getTagById(
            @PathVariable(name = "tagId") Long tagId,
            @Valid @RequestBody SaveTagResource tagRequest) {
        return convertToResource(tagService.updateTag(tagId,convertToEntity(tagRequest)));
    }

    private Tag convertToEntity(SaveTagResource resource) {
        return mapper.map(resource, Tag.class);
    }
    private TagResource convertToResource(Tag entity) {

        return mapper.map(entity, TagResource.class);
    }


}
