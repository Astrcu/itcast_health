package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.Permission;
import com.itheima.pojo.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface RoleDao {
    Set<Role> findRolesByUserId(Integer userId);

    List<Role> findAll();

    Page<Role> findByCondition(String queryString);

    Permission findById(Integer id);

    Integer[] findMenuIdsById(Integer id);

    Integer[] findPermissionIdsById(Integer id);

    void add(Role role);

    void setPermission(@Param("roleId") Integer roleId, @Param("permissionId") Integer permissionId);

    void setMenu(@Param("roleId") Integer roleId, @Param("menuId") Integer menuId);

    void edit(Role role);

    void delRelationPermission(Integer id);

    void delRelationMenu(Integer id);
}
