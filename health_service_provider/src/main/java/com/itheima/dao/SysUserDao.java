package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.SysUser;
import org.apache.ibatis.annotations.Param;

public interface SysUserDao {
    Page<SysUser> findByCondition(String queryString);

    void add(SysUser sysUser);

    void set(@Param("sysUserId") Integer sysUserId, @Param("roleId") Integer roleId);

    void edit(SysUser sysUser);

    void delRelation(Integer id);

    SysUser findById(Integer id);

    Integer[] findRoleIdsById(Integer id);

    long findCountById(Integer id);

    void delById(Integer id);
}
