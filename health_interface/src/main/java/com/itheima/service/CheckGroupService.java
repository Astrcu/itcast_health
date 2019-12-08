package com.itheima.service;


import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.CheckGroup;

import java.util.List;

public interface CheckGroupService {

    void add(Integer[] checkItemIds, CheckGroup checkGroup);

    PageResult findPage(QueryPageBean queryPageBean);

    CheckGroup findById(Integer id);

    Integer[] findCheckItemIdsById(Integer id);

    void edit(Integer[] checkItemIds, CheckGroup checkGroup);

    void delById(Integer id);

    List<CheckGroup> findAll();
}
