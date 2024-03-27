package com.sainoki.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sainoki.domain.ResponseResult;
import com.sainoki.domain.entity.Category;


/**
 * 分类表(Category)表服务接口
 *
 * @author makejava
 * @since 2024-03-16 14:48:26
 */
public interface CategoryService extends IService<Category> {

    ResponseResult getCategoryList();
}

