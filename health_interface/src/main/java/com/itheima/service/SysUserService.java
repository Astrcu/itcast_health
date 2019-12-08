package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.SysUser;

public interface SysUserService {
    PageResult findPage(QueryPageBean queryPageBean);

    void add(Integer[] roleIds, SysUser sysUser);

    void edit(Integer[] roleIds, SysUser sysUser);

    SysUser findById(Integer id);

    Integer[] findRoleIdsById(Integer id);

    void delById(Integer id);

    void delByIds(Integer[] ids);
}
