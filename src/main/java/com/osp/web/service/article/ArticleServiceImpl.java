package com.osp.web.service.article;

import com.osp.common.PagingResult;
import com.osp.model.Article;
import com.osp.web.dao.article.ArticleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Created by Admin on 3/9/2018.
 */
@Service
public class ArticleServiceImpl implements ArticleService{
    @Autowired
    ArticleDao articleDao;

    @Override
    public Optional<PagingResult> listByCate(PagingResult page, Integer categoryId) {
        return articleDao.listByCate(page,categoryId);
    }

    @Override
    public Optional<Article> get(Integer id) {
        return articleDao.getById(id);
    }

    @Override
    public Optional<Article> getArticleOfCate(Integer id, Integer categoryId) {
        return articleDao.getArticleOfCate(id,categoryId);
    }

    @Override
    public Optional<Article> getFirstArticleOfCategory(Integer categoryId) {
        return articleDao.getFirstArticleOfCategory(categoryId);
    }

    @Override
    public Optional<List<Article>> listAllOfCate(Integer categoryId) {
        return articleDao.listAllOfCate(categoryId);
    }
}
