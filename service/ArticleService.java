package com.example.jpa_learn.service;

import com.example.jpa_learn.dto.ArticleDTO;
import com.example.jpa_learn.entity.Article;
import com.example.jpa_learn.param.ArticleParam;
import com.example.jpa_learn.param.JournalQuery;
import com.example.jpa_learn.vo.JournalWithCmtCountDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;

import java.util.List;

public interface ArticleService extends CrudService<Article, Integer>{
    @NonNull
    ArticleDTO convertTo(@NonNull Article article);

    @NonNull
    Article createBy(@NonNull ArticleParam articleParam);

    @NonNull
    Page<Article> pageBy(@NonNull JournalQuery journalQuery, @NonNull Pageable pageable);

    @NonNull
    Page<JournalWithCmtCountDTO> convertToCmtCountDto(@NonNull Page<Article> journalPage);

    @NonNull
    List<JournalWithCmtCountDTO> convertToCmtCountDto(List<Article> journals);

    @NonNull
    Article updateBy(Article article);


}
