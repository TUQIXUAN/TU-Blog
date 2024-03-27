package com.sainoki.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sainoki.constants.SystemConstants;
import com.sainoki.domain.ResponseResult;
import com.sainoki.domain.entity.Article;
import com.sainoki.domain.entity.Category;
import com.sainoki.domain.vo.ArticleDetailVo;
import com.sainoki.domain.vo.ArticleListVo;
import com.sainoki.domain.vo.PageVo;
import com.sainoki.domain.vo.hotArticleVo;
import com.sainoki.mapper.ArticleMapper;
import com.sainoki.service.ArticleService;

import com.sainoki.service.CategoryService;
import com.sainoki.utils.BeanCopyUtils;
import org.springframework.beans.BeanUtils;

import org.springframework.stereotype.Service;


import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {
    @Resource
    private CategoryService categoryService;

    @Override
    public ResponseResult hotArticleList() {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<Article>();
        // 必须是正式文章
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        // 按浏览量降序排序
        queryWrapper.orderByDesc(Article::getViewCount);
        // 最多只查询10条
        Page<Article> pages = new Page<>(1,10);
        // 这里是DAO层方法的使用
        page(pages, queryWrapper);
        List<Article> articles = pages.getRecords();

        List<hotArticleVo> hotArticleVos = BeanCopyUtils.copyBeanList(articles, hotArticleVo.class);

        return ResponseResult.okResult(hotArticleVos);
    }

    @Override
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId) {
        // 查询条件
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        //如果有categoryId，要传入查询
        queryWrapper.eq(Objects.nonNull(categoryId)&&categoryId>0,Article::getCategoryId,categoryId);

        //状态需要是正式发布的
        queryWrapper.eq(Article::getStatus,SystemConstants.ARTICLE_STATUS_NORMAL);
        //对ISTop进行排序
        queryWrapper.orderByDesc(Article::getIsTop);

        // 分页查询
        Page<Article> page = new Page<>(pageNum, pageSize);
        page(page,queryWrapper);

        // 查询categoryName
        List<Article>  articles = page.getRecords();
        articles = articles.stream().map(article -> article.setCategoryName(categoryService.getById(article.getCategoryId()).getName()))
                .collect(Collectors.toList());


        //封装查询结果
        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(articles, ArticleListVo.class);

        PageVo pageVo = new PageVo(articleListVos,page.getTotal());

        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult getArticleDetail(Long id) {

        // 根据ID查询文章
        Article article = getById(id);
        // 转换成VO
        ArticleDetailVo articleDetailVo = BeanCopyUtils.copyBean(article, ArticleDetailVo.class);
        // 根据分类ID查询分类名
        Long categoryId = articleDetailVo.getCategoryId();
        Category category = categoryService.getById(categoryId);
        if (category!=null)
            article.setCategoryName(category.getName());
        // 封装响应
        return ResponseResult.okResult(articleDetailVo);
    }
}
