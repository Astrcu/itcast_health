package com.itheima.service;

import com.itheima.pojo.SysUser;

public interface UserService {
    SysUser findByUsername(String username);
}
