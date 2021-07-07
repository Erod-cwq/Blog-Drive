package com.example.jpa_learn.param;

import com.example.jpa_learn.entity.enums.ArticleType;
import lombok.Data;

@Data
public class JournalQuery {
    private String keyword;

    private ArticleType type;
}
