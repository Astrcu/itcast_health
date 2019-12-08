package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.Permission;

import java.util.List;

public interface PermissionService {
    List<Permission> findAllPermission();

    PageResult findPage(QueryPageBean queryPageBean);

    void add(Permission permission);

    void edit(Permission permission);

    Permission findById(Integer id);

    void delById(Integer id);
}
