package com.hy.qql.demo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hy.qql.demo.entity.Zhaopin;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author qql
 * @since 2022-06-08
 */
public interface IZhaopinService extends IService<Zhaopin> {
    Page<Zhaopin> findPage(Page<Zhaopin> page, @Param("title")String title);
}
