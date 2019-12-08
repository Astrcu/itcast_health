package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;

import com.itheima.constant.MessageConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;

import com.itheima.entity.Result;
import com.itheima.pojo.Menu;
import com.itheima.service.MenuService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/menu")
public class MenuController {

    @Reference
    MenuService menuService;
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        return menuService.findPage(queryPageBean);
    }

    @RequestMapping("/add")
    public Result add(@RequestBody Menu menu){
        try {
            menuService.add(menu);
            return new Result(true,MessageConstant.ADD_MENU_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.ADD_MENU_FAIL);
        }
    }

    @RequestMapping("/edit")
    public Result edit(@RequestBody Menu menu){
        try {
            menuService.edit(menu);
            return new Result(true,MessageConstant.EDIT_MENU_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.EDIT_MENU_FAIL);
        }
    }

    @RequestMapping("/findById")
    public Result findById(Integer id){
        try {
            Menu menu = menuService.findById(id);
            return new Result(true,MessageConstant.QUERY_MENU_SUCCESS,menu);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_MENU_FAIL);
        }
    }
    @RequestMapping("/delById")
    public Result delById(Integer id){
        try {
            menuService.delById(id);
            return new Result(true,MessageConstant.DELETE_MENU_SUCCESS);
        } catch (RuntimeException e) {
            return new Result(false,e.getMessage());
        } catch (Exception e){
            return new Result(false,MessageConstant.DELETE_MENU_FAIL);
        }
    }
    @RequestMapping("/findAllMenu")
    public Result findAllMenu(){
        try {
            List<Menu> menuList = menuService.findAllMenu();
            return new Result(true,"所有菜单查询成功",menuList);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"所有菜单查询失败");
        }

    }

    @RequestMapping("/findMenuByUsername")
    public Result findMenuByUsername(String username){
        try {
            List<Menu> menuList = menuService.findMenuByUsername(username);
            return new Result(true,MessageConstant.QUERY_MENU_SUCCESS,menuList);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true,MessageConstant.QUERY_MENU_FAIL);
        }
    }
}
