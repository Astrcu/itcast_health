package com.itheima.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.PermissionDao;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    PermissionDao permissionDao;
    @Override
    public List<Permission> findAllPermission() {
        return permissionDao.findAllPermission();
    }

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        Page<Permission> page = permissionDao.findByCondition(queryPageBean.getQueryString());
        return new PageResult(page.getTotal(),page);
    }

    @Override
    @Transactional
    public void add(Permission permission) {
        permissionDao.add(permission);
    }

    @Override
    public void edit(Permission permission) {
        permissionDao.edit(permission);
    }

    //编辑数据回显
    @Override
    public Permission findById(Integer id) {
        return permissionDao.findById(id);
    }

    @Override
    public void delById(Integer id) {
        long count = permissionDao.findCountById(id);
        if(count>0){
            throw  new RuntimeException("权限项被角色表关联，不能删除！！！");
        }
        permissionDao.delById(id);
    }
}
