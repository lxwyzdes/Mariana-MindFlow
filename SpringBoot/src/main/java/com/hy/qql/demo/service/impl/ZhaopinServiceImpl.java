package com.hy.qql.demo.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hy.qql.demo.entity.Zhaopin;
import com.hy.qql.demo.mapper.ZhaopinMapper;
import com.hy.qql.demo.service.IZhaopinService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author qql
 * @since 2022-06-08
 */
@Service
public class ZhaopinServiceImpl extends ServiceImpl<ZhaopinMapper, Zhaopin> implements IZhaopinService {

    @Autowired
    private ZhaopinMapper zhaopinMapper;

    @Override
    public Page<Zhaopin> findPage(Page<Zhaopin> page, String title) {
        return zhaopinMapper.findPage(page, title);
    }
}
