package com.sainoki.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sainoki.domain.ResponseResult;
import com.sainoki.domain.entity.Article;

public interface ArticleService extends IService<Article> {
    ResponseResult hotArticleList();

    ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId);

    ResponseResult getArticleDetail(Long id);

}
