package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.Permission;
import com.itheima.pojo.Role;

import java.util.List;

public interface RoleService {
    List<Role> findAll();

    PageResult findPage(QueryPageBean queryPageBean);

    Permission findById(Integer id);

    Integer[] findMenuIdsById(Integer id);

    Integer[] findPermissionIdsById(Integer id);

    void add(Role role, Integer[] permissionIds, Integer[] menuIds);

    void edit(Role role, Integer[] permissionIds, Integer[] menuIds);
}
