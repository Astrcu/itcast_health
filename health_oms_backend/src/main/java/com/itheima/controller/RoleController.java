package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.Permission;
import com.itheima.pojo.Role;
import com.itheima.service.RoleService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Reference
    RoleService roleService;

    //查询所有角色
    @RequestMapping("/findAll")
    public Result findAll(){
        try {
            List<Role> roleList = roleService.findAll();
            return new Result(true,MessageConstant.QUERY_ROLE_SUCCESS,roleList);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_ROLE_FAIL);
        }
    }

    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        return roleService.findPage(queryPageBean);
    }

    //编辑角色之角色基本数据回显
    @RequestMapping("/findById")
    public Result findById(Integer id){
        try {
            Permission permission = roleService.findById(id);
            return new Result(true,"角色基本信息回显成功",permission);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"角色基本信息回显失败");
        }
    }
    //编辑角色之角色菜单项回显
    @RequestMapping("/findMenuIdsById")
    public Result findMenuIdsById(Integer id){
        try {
            Integer[] menuIds = roleService.findMenuIdsById(id);
            return new Result(true,"角色菜单项回显成功",menuIds);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"角色菜单项回显失败");
        }
    }
    //编辑角色之角色权限项回显
    @RequestMapping("/findPermissionIdsById")
    public Result findPermissionIdsById(Integer id){
        try {
            Integer[] permissionIds = roleService.findPermissionIdsById(id);
            return new Result(true,"角色权限项回显成功",permissionIds);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"角色权限项回显失败");
        }
    }
    //添加角色
    @RequestMapping("/add")
    public Result add(@RequestBody Map<String,Object> map){
        JSONObject jsonObject = (JSONObject) map.get("role");
        Role role = jsonObject.toJavaObject(Role.class);
        JSONArray jsonArray1 = (JSONArray)map.get("permissionIds");
        Integer[] permissionIds = jsonArray1.toArray(new Integer[]{});
        JSONArray jsonArray2 = (JSONArray)map.get("menuIds");
        Integer[] menuIds = jsonArray2.toArray(new Integer[]{});

        try {
            roleService.add(role,permissionIds,menuIds);
            return new Result(true,"角色添加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"角色添加失败");
        }
    }
    //修改角色信息
    @RequestMapping("/edit")
    public Result edit(@RequestBody Map<String,Object> map){
        JSONObject jsonObject = (JSONObject) map.get("role");
        Role role = jsonObject.toJavaObject(Role.class);
        JSONArray jsonArray1 = (JSONArray)map.get("permissionIds");
        Integer[] permissionIds = jsonArray1.toArray(new Integer[]{});
        JSONArray jsonArray2 = (JSONArray)map.get("menuIds");
        Integer[] menuIds = jsonArray2.toArray(new Integer[]{});

        try {
            roleService.edit(role,permissionIds,menuIds);
            return new Result(true,"修改角色信息成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"修改角色信息失败");
        }
    }
}
