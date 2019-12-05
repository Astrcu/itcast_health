package com.itheima.service;

import com.itheima.pojo.SysUser;

public interface UserService {

    //根据用户名载入用户信息 包含角色和权限
    SysUser findByUsername(String username);
}
