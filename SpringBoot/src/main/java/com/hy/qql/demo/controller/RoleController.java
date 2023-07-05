package com.hy.qql.demo.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hy.qql.demo.common.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hy.qql.demo.service.IRoleService;
import com.hy.qql.demo.entity.Role;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author qql
 * @since 2022-03-30
 */
@RestController
@RequestMapping("/role")
public class RoleController {
    @Resource
    private IRoleService roleService;

    @PostMapping
    public Boolean save(@RequestBody Role role) {
        return roleService.saveOrUpdate(role);
    }

    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable Integer id) {
        return roleService.removeById(id);
    }

    @GetMapping
    public List<Role> findAll() {
        return roleService.list();
    }

    @GetMapping("/{id}")
    public Role findOne(@PathVariable Integer id) {
        return roleService.getById(id);
    }

    @PostMapping("/del/batch")
    public boolean deleteBatch(@RequestBody List<Integer> ids) {
        return roleService.removeByIds(ids);
    }

    @GetMapping("/page")
    public Page<Role> findPage(
            @RequestParam String name,
            @RequestParam Integer pageNum,
            @RequestParam Integer pageSize
    ) {
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name", name);
        queryWrapper.orderByDesc("id");
        return roleService.page(new Page<>(pageNum, pageSize), queryWrapper);
    }


//    绑定角色和菜单关系 roleId角色id menuIds菜单id
    @PostMapping("/roleMenu/{roleId}")
        public Result roleMneu(@PathVariable Integer roleId,@RequestBody List<Integer> menuIds){
            roleService.setRoleMenu(roleId,menuIds);
            return Result.success();
    }

    @GetMapping("/roleMenu/{roleId}")
    public Result getRoleMenu(@PathVariable Integer roleId){
        return  Result.success(roleService.getRoleMenu(roleId));
    }

}

