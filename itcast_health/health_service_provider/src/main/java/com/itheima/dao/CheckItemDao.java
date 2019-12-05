package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.CheckItem;

import java.util.List;

public interface CheckItemDao {

    void add(CheckItem checkItem);

    Page<CheckItem> findByCondition(String queryString);

    CheckItem findById(Integer id);

    void edit(CheckItem checkItem);

    long findCountById(Integer id);

    void delById(Integer id);

    List<CheckItem> findAll();

    //根据检查组id获取检查项信息
    List<CheckItem> findCheckItemsByCheckGroupId(Integer checkGroupId);
}
