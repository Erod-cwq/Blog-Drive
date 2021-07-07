package com.example.jpa_learn.repository;

import com.example.jpa_learn.entity.Attachment;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AttachmentRepository extends BaseRepository<Attachment, Integer>,
        JpaSpecificationExecutor<Attachment> {

    @Query(value = "select distinct a.mediaType from Attachment a")
    List<String> findAllMediaType();
}
