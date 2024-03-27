package com.sainoki.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sainoki.constants.SystemConstants;
import com.sainoki.domain.ResponseResult;
import com.sainoki.domain.entity.Link;
import com.sainoki.domain.vo.LinkListVo;
import com.sainoki.mapper.LinkMapper;

import com.sainoki.service.LinkService;
import com.sainoki.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 友链(Link)表服务实现类
 *
 * @author makejava
 * @since 2024-03-18 16:54:17
 */
@Service("linkService")
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements LinkService {

    @Override
    public ResponseResult getAllLink() {
        LambdaQueryWrapper<Link> LinkQueryWrapper = new LambdaQueryWrapper<>();
        LinkQueryWrapper.eq(Link::getStatus, SystemConstants.Link_STATUS_NORMAL);
        List<Link> linkList = list(LinkQueryWrapper);
        // 转换成VO
        List<LinkListVo> linkListVos = BeanCopyUtils.copyBeanList(linkList, LinkListVo.class);

        return ResponseResult.okResult(linkListVos);
    }
}

