package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.Permission;

import java.util.List;
import java.util.Set;

public interface PermissionDao {
    Set<Permission> findPermissionByRoleId(Integer roleId);

    List<Permission> findAllPermission();

    Page<Permission> findByCondition(String queryString);

    void add(Permission permission);

    void edit(Permission permission);

    Permission findById(Integer id);

    long findCountById(Integer id);

    void delById(Integer id);
}
