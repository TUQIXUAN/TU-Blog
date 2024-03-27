package com.sainoki.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.sainoki.constants.SystemConstants;
import com.sainoki.domain.ResponseResult;
import com.sainoki.domain.entity.Article;
import com.sainoki.domain.entity.Category;
import com.sainoki.domain.vo.CategoryVO;
import com.sainoki.mapper.CategoryMapper;
import com.sainoki.service.ArticleService;
import com.sainoki.service.CategoryService;
import com.sainoki.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 分类表(Category)表服务实现类
 *
 * @author makejava
 * @since 2024-03-16 14:48:26
 */
@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Resource
    private ArticleService articleService;

    @Override
    public ResponseResult getCategoryList() {
        // 查询文章，状态为已发布
        LambdaQueryWrapper<Article> articleQueryWrapper = new LambdaQueryWrapper<>();
        articleQueryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        List<Article> articleList = articleService.list(articleQueryWrapper);
        // 获取文章的分类ID，并去重
        Set<Long> CategoryIds = articleList.stream()
                .map(article -> article.getCategoryId())
                .collect(Collectors.toSet());

        // 查询分类表
        List<Category> categories = this.listByIds(CategoryIds);
        categories= categories.stream()
                .filter(category -> SystemConstants.STATUS_NORMAL.equals(category.getStatus()))
                .collect(Collectors.toList());
        // 封装vo
        List<CategoryVO> categoryVOs = BeanCopyUtils.copyBeanList(categories, CategoryVO.class);

        return ResponseResult.okResult(categoryVOs);
    }
}

