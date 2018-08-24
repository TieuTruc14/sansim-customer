package com.osp.web.controller;

import com.osp.common.PagingResult;
import com.osp.model.Article;
import com.osp.web.service.article.ArticleService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by Admin on 3/8/2018.
 */
@Controller
@RequestMapping("/category")
public class CategoryController {
    private Logger logger= LogManager.getLogger(CategoryController.class);
    @Autowired
    ArticleService articleService;

    @GetMapping("/guide")
    public String listGuide(){

        return "news.list";
    }





}
