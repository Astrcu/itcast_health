package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.Menu;

import java.util.List;


public interface MenuDao {
    Page<Menu> findByCondition(String queryString);

    void add(Menu menu);

    void edit(Menu menu);

    Menu findById(Integer id);

    long findCountById(Integer id);

    void delById(Integer id);

    List<Menu> findAllMenu();

    List<Menu> findMenuByUsername(String username);

    List<Menu> findMenuByParentId(String username, Integer id);
}
