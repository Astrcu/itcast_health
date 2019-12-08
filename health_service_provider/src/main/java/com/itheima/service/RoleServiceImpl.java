package com.itheima.service;


import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.RoleDao;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.Permission;
import com.itheima.pojo.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService{

    @Autowired
    RoleDao roleDao;
    //查询所有
    @Override
    public List<Role> findAll() {
        return roleDao.findAll();
    }
    //分页查询
    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());

        Page<Role> page = roleDao.findByCondition(queryPageBean.getQueryString());

        return new PageResult(page.getTotal(),page);
    }

    @Override
    public Permission findById(Integer id) {
        return roleDao.findById(id);
    }

    //角色之菜单项回显
    @Override
    public Integer[] findMenuIdsById(Integer id) {
        return roleDao.findMenuIdsById(id);
    }

    //角色之权限项回显
    @Override
    public Integer[] findPermissionIdsById(Integer id) {
        return roleDao.findPermissionIdsById(id);
    }

    //添加角色
    @Override
    @Transactional
    public void add(Role role, Integer[] permissionIds, Integer[] menuIds) {
        roleDao.add(role);
        if(role.getId()!=null && ((permissionIds !=null && permissionIds.length>0) || (menuIds!=null && menuIds.length>0))){
            for (Integer permissionId : permissionIds) {
                roleDao.setPermission(role.getId(),permissionId);
            }
            for (Integer menuId : menuIds) {
                roleDao.setMenu(role.getId(),menuId);
            }
        }
    }

    //修改角色信息
    @Override
    public void edit(Role role, Integer[] permissionIds, Integer[] menuIds) {
        roleDao.edit(role);
        //先删除关系，再重新设置关系
        roleDao.delRelationPermission(role.getId());
        roleDao.delRelationMenu(role.getId());
        if((permissionIds != null && permissionIds.length>0)||(menuIds != null && menuIds.length>0)){
            for (Integer permissionId : permissionIds) {
                roleDao.setPermission(role.getId(),permissionId);
            }
            for (Integer menuId : menuIds) {
                roleDao.setMenu(role.getId(),menuId);
            }
        }
    }
}
