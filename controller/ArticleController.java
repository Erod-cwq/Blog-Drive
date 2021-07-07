package com.example.jpa_learn.controller;
import com.example.jpa_learn.dto.ArticleDTO;
import com.example.jpa_learn.param.ArticleParam;
import com.example.jpa_learn.param.JournalQuery;
import com.example.jpa_learn.vo.JournalWithCmtCountDTO;
import io.swagger.annotations.ApiOperation;
import com.example.jpa_learn.service.ArticleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
import com.example.jpa_learn.entity.Article;
import javax.validation.Valid;

import static org.springframework.data.domain.Sort.Direction.DESC;

@RestController
@RequestMapping("/admin/articles")
public class ArticleController {
    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }
    @PostMapping
    @ApiOperation("Creates an article")
    public ArticleDTO creatBy(@RequestBody @Valid ArticleParam articleParam){
        Article createdArticle = articleService.createBy(articleParam);
        return articleService.convertTo(createdArticle);
    }

    @GetMapping
    @ApiOperation("List articles")
    public Page<JournalWithCmtCountDTO> pageBy(@PageableDefault(sort = "createTime", direction = DESC)Pageable pageable,
                                               JournalQuery journalQuery){
        Page<Article> articlePage = articleService.pageBy(journalQuery, pageable);
        return articleService.convertToCmtCountDto(articlePage);
    }

    @PutMapping("{id:\\d+}")
    @ApiOperation("Updates an article")
    public ArticleDTO updateBy(@PathVariable("id") Integer id,
                               @RequestBody @Valid ArticleParam articleParam){
        Article article = articleService.getById(id);
        articleParam.update(article);
        Article updatedArticle = articleService.updateBy(article);
        return new ArticleDTO().convertFrom(updatedArticle);
    }

    @DeleteMapping("{id:\\d+}")
    @ApiOperation("Deletes an article")
    public ArticleDTO deleteBy(@PathVariable("id") Integer id){
        Article deletedArticle = articleService.removeById(id);
        return articleService.convertTo(deletedArticle);
    }


}
