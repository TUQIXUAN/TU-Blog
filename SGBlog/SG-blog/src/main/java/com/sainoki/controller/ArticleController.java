package com.sainoki.controller;

import com.sainoki.domain.ResponseResult;
import com.sainoki.domain.entity.Article;
import com.sainoki.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Resource
    private ArticleService articleService;


    @GetMapping("/hotArticleList")
    public ResponseResult hotArticleList(){
        // 查询热门文章
        ResponseResult result=articleService.hotArticleList();
        return result;
    }
    @GetMapping("/articleList")
    public ResponseResult articleList(Integer pageNum,Integer pageSize, Long categoryId){

        return articleService.articleList(pageNum,pageSize,categoryId);
    }
    @GetMapping("/articleDetail/{id}")
    public ResponseResult getarticleDetail(@PathVariable("id") Long id){
        return articleService.getArticleDetail(id);
    }
}
