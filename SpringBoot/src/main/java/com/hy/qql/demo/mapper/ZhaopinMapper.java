package com.hy.qql.demo.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hy.qql.demo.entity.Zhaopin;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author qql
 * @since 2022-06-08
 */
public interface ZhaopinMapper extends BaseMapper<Zhaopin> {
    Page<Zhaopin> findPage(Page<Zhaopin> page, @Param("title")String title);
}
