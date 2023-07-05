package com.hy.qql.demo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hy.qql.demo.common.Result;
import com.hy.qql.demo.entity.Gong;
import com.hy.qql.demo.entity.Xue;
import com.hy.qql.demo.entity.Yuan;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author qql
 * @since 2022-05-10
 */
public interface IYuanService extends IService<Yuan> {
  Page<Yuan> findPage(Page<Yuan> page,String yname);

    List<Xue> findAll();

  List<Gong> GoodFindAll();
}
