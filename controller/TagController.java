package com.example.jpa_learn.controller;


import com.example.jpa_learn.dto.post.TagDTO;
import com.example.jpa_learn.entity.Tag;
import com.example.jpa_learn.param.TagParam;
import com.example.jpa_learn.service.PostTagService;
import com.example.jpa_learn.service.TagService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/admin/tags")
@Slf4j
public class TagController {
    private final TagService tagService;
    private final PostTagService postTagService;

    public TagController(TagService tagService, PostTagService postTagService)
    {
        this.tagService = tagService;
        this.postTagService = postTagService;
    }

    @GetMapping
    @ApiOperation("list all tags")
    public List<? extends TagDTO> listTags(
            @SortDefault(sort = "createTime", direction = Sort.Direction.DESC) Sort sort,
            @RequestParam(name="more", required = false, defaultValue = "false") Boolean more
    ){
        return tagService.convertTo(tagService.listAll());
    }

    @PostMapping
    @ApiOperation("Creat a tag")
    public TagDTO createTag(@Valid @RequestBody TagParam tagParam){
        Tag tag = tagParam.convertTo();
        log.debug("tag to be created [{}]", tag);
        return tagService.convertTo(tagService.create(tag));

    }
}
