package com.sainoki.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sainoki.domain.ResponseResult;
import com.sainoki.domain.entity.Link;


/**
 * 友链(Link)表服务接口
 *
 * @author makejava
 * @since 2024-03-18 16:54:17
 */
public interface LinkService extends IService<Link> {

    ResponseResult getAllLink();
}

