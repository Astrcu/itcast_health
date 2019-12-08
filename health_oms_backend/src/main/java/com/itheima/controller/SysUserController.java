package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.SysUser;
import com.itheima.service.SysUserService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequestMapping("/sysUser")
public class SysUserController {

    @Reference
    SysUserService sysUserService;

    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        return sysUserService.findPage(queryPageBean);
    }

    //添加用户
    @RequestMapping("/add")
    public Result add(Integer[] roleIds, @RequestBody SysUser sysUser){
        try {
            sysUserService.add(roleIds,sysUser);
            return new Result(true,"用户添加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"用户添加失败");
        }
    }
    //修改用户
    @RequestMapping("/edit")
    public Result edit(Integer[] roleIds, @RequestBody SysUser sysUser){
        try {
            sysUserService.edit(roleIds,sysUser);
            return new Result(true,"用户信息修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"用户信息修改失败");
        }
    }
    //修改之数据回显
    @RequestMapping("/findById")
    public Result findById(Integer id){
        try {
            SysUser sysUser =  sysUserService.findById(id);
            return new Result(true,"数据回显成功",sysUser);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"数据回显失败");
        }
    }
    //修改值角色项回显
    @RequestMapping("/findRoleIdsById")
    public Result findRoleIdsById(Integer id){
        try {
            Integer[] roleIds = sysUserService.findRoleIdsById(id);
            System.out.println(Arrays.toString(roleIds));
            return new Result(true,"角色项回显成功",roleIds);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"角色项回显失败");
        }
    }
    //删除一个角色
    @RequestMapping("/delById")
    public Result delById(Integer id){
        try {
            sysUserService.delById(id);
            return new Result(true,"删除成功");
        } catch (RuntimeException e) {
            return new Result(false,e.getMessage());
        }catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"删除失败");
        }
    }

    //批量删除
    @RequestMapping("/delByIds")
    public Result delByIds(Integer[] ids){
        try {
            sysUserService.delByIds(ids);
            return new Result(true,"批量删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"批量删除失败");
        }
    }
}
