package com.hy.qql.demo.service;

import com.hy.qql.demo.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author qql
 * @since 2022-03-30
 */
public interface IMenuService extends IService<Menu> {

    List<Menu> findMenus(String name);
}
