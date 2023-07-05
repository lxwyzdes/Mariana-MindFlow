package com.hy.qql.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hy.qql.demo.common.Result;
import com.hy.qql.demo.entity.Gong;
import com.hy.qql.demo.entity.Xue;
import com.hy.qql.demo.entity.Yuan;
import com.hy.qql.demo.mapper.YuanMapper;
import com.hy.qql.demo.service.IYuanService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author qql
 * @since 2022-05-10
 */
@Service
public class YuanServiceImpl extends ServiceImpl<YuanMapper, Yuan> implements IYuanService {

    @Resource
    private YuanMapper yuanMapper;

    @Override
    public Page<Yuan> findPage(Page<Yuan> page, String yname) {
        return yuanMapper.findPage(page,yname);
    }

    @Override
    public List<Xue> findAll() {
        return yuanMapper.findAll();
    }

    @Override
    public List<Gong> GoodFindAll() {
        return yuanMapper.GoodFindAll();
    }
}
