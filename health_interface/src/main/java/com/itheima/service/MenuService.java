package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.Menu;

import java.util.List;

public interface MenuService {
    PageResult findPage(QueryPageBean queryPageBean);

    void add( Menu menu);

    void edit(Menu menu);

    Menu findById(Integer id);

    void delById(Integer id);

    List<Menu> findAllMenu();

    List<Menu> findMenuByUsername(String username);
}
