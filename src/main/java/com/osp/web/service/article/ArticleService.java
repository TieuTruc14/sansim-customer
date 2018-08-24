package com.osp.web.service.article;

import com.osp.common.PagingResult;
import com.osp.model.Article;

import java.util.List;
import java.util.Optional;

/**
 * Created by Admin on 3/9/2018.
 */
public interface ArticleService {
    Optional<PagingResult> listByCate(PagingResult page,Integer categoryId);
    Optional<Article> get(Integer id);
    Optional<Article> getArticleOfCate(Integer id,Integer categoryId);
    Optional<Article> getFirstArticleOfCategory(Integer categoryId);
    Optional<List<Article>> listAllOfCate(Integer categoryId);
}
