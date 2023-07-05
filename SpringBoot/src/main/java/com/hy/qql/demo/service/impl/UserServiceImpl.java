package com.hy.qql.demo.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.log.Log;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hy.qql.demo.common.Constants;
import com.hy.qql.demo.controller.DTO.UserDTO;
import com.hy.qql.demo.controller.DTO.UserPasswordDTO;
import com.hy.qql.demo.entity.Menu;
import com.hy.qql.demo.entity.User;
import com.hy.qql.demo.exception.ServiceException;
import com.hy.qql.demo.mapper.RoleMapper;
import com.hy.qql.demo.mapper.RoleMenuMapper;
import com.hy.qql.demo.mapper.UserMapper;
import com.hy.qql.demo.service.IMenuService;
import com.hy.qql.demo.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hy.qql.demo.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author qql
 * @since 2022-03-27
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    private static final Log LOG = Log.get();

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private RoleMenuMapper roleMenuMapper;

    @Resource
    private IMenuService iMenuService;

    @Resource
    private UserMapper userMapper;

//    @Override
//    public UserDTO login(UserDTO userDTO) {
//        User one = getUserInfo(userDTO);
//        if (one != null) {
//            BeanUtil.copyProperties(one, userDTO, true);
//            // 设置token
//            String token = TokenUtils.genToken(one.getId().toString(), one.getPassword());
//            userDTO.setToken(token);
//
//            String role = one.getRole();
//
////            设置用户的菜单列表
//            List<Menu> roleMenus = getRoleMenus(role);
//            userDTO.setMenus(roleMenus);
//            return userDTO;
//        } else {
//            throw new ServiceException(Constants.CODE_600, "用户名或密码错误");
//        }
//    }

    @Override
    public UserDTO login(UserDTO userDTO) {
        User one = getUserInfo(userDTO);
        if (one != null) {
            BeanUtil.copyProperties(one, userDTO, true);
            // 设置token
            String token = TokenUtils.genToken(one.getId().toString(), one.getPassword());
            userDTO.setToken(token);

            String role = one.getRole(); // ROLE_ADMIN
            // 设置用户的菜单列表
            List<Menu> roleMenus = getRoleMenus(role);
            System.out.println(roleMenus);
            userDTO.setMenus(roleMenus);
            return userDTO;
        } else {
            throw new ServiceException(Constants.CODE_600, "用户名或密码错误");
        }
    }

    @Override
    public User register(UserDTO userDTO) {
        User one = getUserInfo(userDTO);
        if (one == null) {
            one = new User();
            BeanUtil.copyProperties(userDTO, one, true);
//            把copy完之后的用户对象储存到数据库
            save(one);
        } else {
            throw new ServiceException(Constants.CODE_600, "用户已存在");
        }
        return one;
    }

    @Override
    public void updatePassword(UserPasswordDTO userPasswordDTO) {
//        int update = userMapper.updatePassword(userPasswordDTO);
        int update = userMapper.updatePassword(userPasswordDTO);
        if (update < 1) {
            throw new ServiceException(Constants.CODE_600, "密码错误");
        }
    }

    @Override
    public Page<User> findPage(Page<User> page, String username, String email, String address) {
        return userMapper.findPage(page,username,email,address);
    }

    private User getUserInfo(UserDTO userDTO) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", userDTO.getUsername());
        queryWrapper.eq("password", userDTO.getPassword());
        User one;
        try {
            one = getOne(queryWrapper); // 从数据库查询用户信息
        } catch (Exception e) {
            LOG.error(e);
            throw new ServiceException(Constants.CODE_500, "系统错误");
        }
        return one;
    }

//    获取当前角色的菜单列表
    private List<Menu> getRoleMenus(String roleFlag){
        Integer roleId = roleMapper.selectByFlag(roleFlag);
        //            当前角色的所有菜单id集合
        List<Integer> menuIds = roleMenuMapper.selectByRoleId(roleId);

//            查询系统所有菜单
        List<Menu> menus = iMenuService.findMenus("");
//            new一个最后筛选完成之后的list
        List<Menu> roleMenus = new ArrayList<>();
//            筛选当前用户角色的菜单
        for (Menu menu : menus) {
            if(menuIds.contains(menu.getId())){
                roleMenus.add(menu);
            }
            List<Menu> children = menu.getChildren();
//                removeIf 移除children 里面不在 menuIds集合中的元素
            children.removeIf(child -> !menuIds.contains(child.getId()));
        }
        return roleMenus;
    }


}
