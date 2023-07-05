package com.hy.qql.demo.service;

import com.hy.qql.demo.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface IMenuService extends IService<Menu> {

    List<Menu> findMenus(String name);
}
