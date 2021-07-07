package com.example.jpa_learn.service;

import com.example.jpa_learn.dto.AttachmentDTO;
import com.example.jpa_learn.entity.Attachment;
import com.example.jpa_learn.param.AttachmentQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AttachmentService {

    @NonNull
    Attachment upload(@NonNull MultipartFile file);

    @NonNull
    AttachmentDTO convertToDto(@NonNull Attachment attachment);

    @NonNull
    Page<AttachmentDTO> pageDtosBy(Pageable pageable, AttachmentQuery attachmentQuery);

    @NonNull
    List<String> listAllMediaType();

    @NonNull
    Attachment removePermanently(@NonNull Integer id);
}
