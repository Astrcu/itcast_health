package com.itheima.dao;

import com.itheima.pojo.SysUser;

public interface UserDao {
    SysUser findByUsername(String username);

}
