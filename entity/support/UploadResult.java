package com.example.jpa_learn.entity.support;

import com.example.jpa_learn.dto.OutputConverter;
import lombok.Data;
import org.springframework.http.MediaType;

@Data
public class UploadResult {
    private String filename;

    private String filePath;

    private MediaType mediaType;

    private Long size;

    private String Key;

    private String thumbPath;
}
