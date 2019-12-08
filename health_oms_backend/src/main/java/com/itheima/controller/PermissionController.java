package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.Permission;
import com.itheima.service.PermissionService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/permission")
public class PermissionController {

    @Reference
    PermissionService permissionService;

    //查询所有权限
    @RequestMapping("/findAllPermission")
    public Result findAllPermission(){
        try {
            List<Permission> permissionList = permissionService.findAllPermission();
            return new Result(true,"查询所有权限成功",permissionList);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"查询所有权限失败");
        }
    }
    //角色分页查询
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        return permissionService.findPage(queryPageBean);
    }

    //添加权限
    @RequestMapping("/add")
    public Result add(@RequestBody Permission permission){
        try {
            permissionService.add(permission);
            return new Result(true,"权限添加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"权限添加失败");
        }
    }
    //修改权限
    @RequestMapping("/edit")
    public Result edit(@RequestBody Permission permission){
        try {
            permissionService.edit(permission);
            return new Result(true,"修改权限成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"修改权限失败");
        }
    }
    //修改权限数据回显
    @RequestMapping("/findById")
    public Result findById(Integer id){
        Permission permission = permissionService.findById(id);
        return new Result(true,"数据回显成功",permission);
    }

    //删除权限数据
    @RequestMapping("/delById")
    public Result delById(Integer id){
        try {
            permissionService.delById(id);
            return new Result(true,"删除权限数据成功");
        } catch (RuntimeException e) {
            return new Result(false,e.getMessage());
        } catch (Exception e){
            return new Result(false,"删除权限数据失败");
        }
    }
}
