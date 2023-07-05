package com.hy.qql.demo.service;

import com.hy.qql.demo.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface IRoleService extends IService<Role> {

    void setRoleMenu(Integer roleId, List<Integer> menuIds);

    List<Integer> getRoleMenu(Integer roleId);
}
