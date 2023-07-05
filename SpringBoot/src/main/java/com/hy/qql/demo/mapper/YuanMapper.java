package com.hy.qql.demo.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hy.qql.demo.entity.Gong;
import com.hy.qql.demo.entity.Xue;
import com.hy.qql.demo.entity.Yuan;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author qql
 * @since 2022-05-10
 */
public interface YuanMapper extends BaseMapper<Yuan> {
    Page<Yuan> findPage(Page<Yuan> page, @Param("yname")String yname);

    List<Xue> findAll();

    List<Gong> GoodFindAll();
}
