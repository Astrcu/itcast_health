package com.itheima.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.SysUserDao;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Service
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    SysUserDao sysUserDao;
    @Autowired
    BCryptPasswordEncoder passwordEncoder;


    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        Page<SysUser> page = sysUserDao.findByCondition(queryPageBean.getQueryString());
        return new PageResult(page.getTotal(),page);
    }

    //添加用户
    @Override
    public void add(Integer[] roleIds, SysUser sysUser) {
        String encode = passwordEncoder.encode(sysUser.getPassword());
        sysUser.setPassword(encode);
        sysUserDao.add(sysUser);
        if(sysUser.getId()!=null && roleIds != null && roleIds.length>0){
            for (Integer roleId : roleIds) {
                sysUserDao.set(sysUser.getId(),roleId);
            }
        }

    }

    //修改用户
    @Override
    public void edit(Integer[] roleIds, SysUser sysUser) {
        String password = sysUser.getPassword();
        if (password != null && !"".equals(password)) {
            String encode = passwordEncoder.encode(password);
            sysUser.setPassword(encode);
        }else {
            password=null;
        }
        sysUserDao.edit(sysUser);
        //先删除关系，再重置关系
        sysUserDao.delRelation(sysUser.getId());
        if(roleIds != null && roleIds.length>0){
            for (Integer roleId : roleIds) {
                sysUserDao.set(sysUser.getId(),roleId);
            }
        }
    }

    //修改用户之用户数据回显
    @Override
    public SysUser findById(Integer id) {
        SysUser sysUser = sysUserDao.findById(id);
        //设置密码为null
        sysUser.setPassword(null);
        return sysUser;
    }

    @Override
    public Integer[] findRoleIdsById(Integer id) {
        return sysUserDao.findRoleIdsById(id);
    }

    @Override
    public void delById(Integer id) {
        long count = sysUserDao.findCountById(id);
        if(count>0){
            throw new RuntimeException("用户与角色有关联关联，不能删除!!!");
        }
        sysUserDao.delRelation(id);
        sysUserDao.delById(id);
    }

    @Override
    public void delByIds(Integer[] ids) {
        if(ids !=null && ids.length>0){
            for (Integer id : ids) {
                delById(id);
            }
        }
    }
}
