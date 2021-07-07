package com.example.jpa_learn.service.impl;

import com.example.jpa_learn.dto.ArticleDTO;
import com.example.jpa_learn.entity.Article;
import com.example.jpa_learn.param.ArticleParam;
import com.example.jpa_learn.param.JournalQuery;
import com.example.jpa_learn.repository.ArticleRepository;
import com.example.jpa_learn.service.ArticleService;
import com.example.jpa_learn.service.base.AbstractCrudService;
import com.example.jpa_learn.utils.ServiceUtils;
import com.example.jpa_learn.vo.JournalWithCmtCountDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import javax.persistence.criteria.Predicate;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ArticleServiceImplement extends AbstractCrudService<Article, Integer> implements ArticleService{
    private final ArticleRepository articleRepository;

    public ArticleServiceImplement(ArticleRepository articleRepository) {
        super(articleRepository);
        this.articleRepository = articleRepository;
    }
    @Override
    public ArticleDTO convertTo(Article article){
        Assert.notNull(article, "Journal must not be null");

        return new ArticleDTO().convertFrom(article);
    }

    @Override
    public Article updateBy(Article article){
        Assert.notNull(article, "article must not be null");
        article.setContent(article.getSourceContent());
        return update(article);
    }



    @Override
    public Article createBy(ArticleParam articleParam) {
        Assert.notNull(articleParam, "Journal param must not be null");
        Article article = articleParam.convertTo();
        article.setContent(article.getSourceContent());
        return create(article);
    }

    @Override
    public Page<Article> pageBy(JournalQuery journalQuery, Pageable pageable){
        Assert.notNull(journalQuery, "Journal type must not be null");
        Assert.notNull(pageable, "Page info must not be null");
        return articleRepository.findAll(buildSpecByQuery(journalQuery), pageable);

    }

    @Override
    public List<JournalWithCmtCountDTO> convertToCmtCountDto(List<Article> articles){
        if(CollectionUtils.isEmpty(articles)){
            return Collections.emptyList();
        }
        Set<Integer> ids = ServiceUtils.fetchProperty(articles, Article::getId);

        return articles.stream().map(
                article -> {
                    return new JournalWithCmtCountDTO().<JournalWithCmtCountDTO>convertFrom(article);
                }).collect(Collectors.toList());
    }

    @Override
    public Page<JournalWithCmtCountDTO> convertToCmtCountDto(Page<Article> journalPage){
        Assert.notNull(journalPage, "Journal page must not be null");
        List<JournalWithCmtCountDTO> journalWithCmtCountDTOS =
                convertToCmtCountDto(journalPage.getContent());
        return new PageImpl<>(journalWithCmtCountDTOS, journalPage.getPageable(),
                journalPage.getTotalElements());

    }

    @NonNull
    private Specification<Article> buildSpecByQuery(@NonNull JournalQuery journalQuery){
        Assert.notNull(journalQuery, "Journal query must not be null");
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new LinkedList<>();
            if (journalQuery.getType()!=null){
                predicates.add(criteriaBuilder.equal(root.get("type"), journalQuery.getType()));
            }
            if(journalQuery.getKeyword()!=null){
                String likeCondition = String.format("%%s%%", StringUtils.strip(journalQuery.getKeyword()));
                Predicate contentLike = criteriaBuilder.like(root.get("content"), likeCondition);
                predicates.add(criteriaBuilder.or(contentLike));
            }
            return query.where(predicates.toArray(new Predicate[0])).getRestriction();
        };
    }



}

