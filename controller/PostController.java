package com.example.jpa_learn.controller;

import com.example.jpa_learn.dto.post.BasePostSimpleDTO;
import com.example.jpa_learn.entity.Post;
import com.example.jpa_learn.entity.PostPicture;
import com.example.jpa_learn.param.PostParam;
import com.example.jpa_learn.param.PostQuery;
import com.example.jpa_learn.service.OptionService;
import com.example.jpa_learn.service.PostPictureService;
import com.example.jpa_learn.service.PostService;
import com.example.jpa_learn.vo.PostDetailVO;
import com.example.jpa_learn.vo.PostPictureDTO;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import static org.springframework.data.domain.Sort.Direction.DESC;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestController
@RequestMapping("/admin/posts")
public class PostController {
    private final PostService postService;
    private final OptionService optionService;
    private final PostPictureService postPictureService;

    public PostController(PostService postService,
                          OptionService optionService,
                          PostPictureService postPictureService) {
        this.postService = postService;
        this.optionService = optionService;
        this.postPictureService = postPictureService;
    }

    @GetMapping
    @ApiOperation("List posts")
    public Page<? extends BasePostSimpleDTO> pageBy(
            @PageableDefault(sort = {"topPriority", "createTime"}, direction = DESC)Pageable pageable,
            PostQuery postQuery, @RequestParam(value = "more", defaultValue = "true") Boolean more){
        Page<Post> postPage = postService.pageBy(postQuery, pageable);
        if(more){
            return postService.convertToListVo(postPage, true);
        }
        return postService.convertToSimple(postPage);

    }

    @PostMapping
    @ApiOperation("Create a post")
    public PostDetailVO createBy(@Valid @RequestBody PostParam postParam){
        Post post = postParam.convertTo();
        return postService.createBy(post, postParam.getTagIds(), postParam.getPictureIds());
    }

    @GetMapping("{postId:\\d+}")
    @ApiOperation("Get a post")
    public PostDetailVO getBy(@PathVariable("postId") Integer postId){
        Post post = postService.getById(postId);
        return postService.convertToDetailVo(post);
    }

    @PutMapping("{postId:\\d+}")
    @ApiOperation("Update a post")
    public PostDetailVO updateBy(@PathVariable("postId") Integer postId,
                                 @Valid @RequestBody PostParam postParam){
        Post postToUpdate = postService.getById(postId);
        postParam.update(postToUpdate);
        return postService.updateBy(postToUpdate, postParam.getTagIds(), postParam.getPictureIds());
    }

    @DeleteMapping("{postId:\\d+}")
    @ApiOperation("Delete a post")
    public PostDetailVO deleteBy(@PathVariable("postId") Integer postId){
        return postService.convertToDetailVo(postService.removeById(postId));
    }

    @PostMapping("/picture")
    @ApiOperation("Create a post picture")
    public PostPictureDTO createPostPicture(@RequestPart("file") MultipartFile file){
        PostPictureDTO postPictureDTO = new PostPictureDTO();
        return postPictureDTO.convertFrom(postPictureService.createBy(file));
    }

}
