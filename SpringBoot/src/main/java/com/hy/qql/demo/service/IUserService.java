package com.hy.qql.demo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hy.qql.demo.controller.DTO.UserDTO;
import com.hy.qql.demo.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import com.hy.qql.demo.controller.DTO.UserPasswordDTO;
/**
 * <p>
 *  服务类
 * </p>
 *
 * @author qql
 * @since 2022-03-27
 */
public interface IUserService extends IService<User> {

    UserDTO login(UserDTO userDTO);

    User register(UserDTO userDTO);

    void updatePassword(UserPasswordDTO userPasswordDTO);

    Page<User> findPage(Page<User> objectPage, String username, String email, String address);
}
