package com.example.jpa_learn.controller;


import com.example.jpa_learn.dto.AttachmentDTO;
import com.example.jpa_learn.param.AttachmentQuery;
import com.example.jpa_learn.service.AttachmentService;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.springframework.data.domain.Sort.Direction.DESC;
@RestController
@RequestMapping("/admin/attachment")
public class AttachmentController {
    private final AttachmentService attachmentService;

    public AttachmentController(AttachmentService attachmentService) {
        this.attachmentService = attachmentService;
    }

    @GetMapping
    public Page<AttachmentDTO> pageBy(
            @PageableDefault(sort = "createTime", direction = DESC)Pageable pageable,
            AttachmentQuery attachmentQuery){
        return attachmentService.pageDtosBy(pageable, attachmentQuery);

    }

    @PostMapping("upload")
    @ApiOperation("upload a single file")
    public AttachmentDTO uploadAttachment(@RequestPart("file")MultipartFile file){
        return attachmentService.convertToDto(attachmentService.upload(file));
    }

    @GetMapping("media_type")
    @ApiOperation("List all media Type")
    public List<String> listMediaType(){return attachmentService.listAllMediaType();}

    @DeleteMapping("{id:\\d+}")
    @ApiOperation("Deletes attachment permanently by id")
    public AttachmentDTO deletePermanently(@PathVariable("id") Integer id) {
        return attachmentService.convertToDto(attachmentService.removePermanently(id));
    }
}
