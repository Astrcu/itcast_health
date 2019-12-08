package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.CheckGroup;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CheckGroupDao {
    void add(CheckGroup checkGroup);

    void set(@Param("checkGroupId") Integer checkGroupId, @Param("checkItemId") Integer checkItemId);

    Page<CheckGroup> findByCondition(String queryString);

    CheckGroup findById(Integer id);

    Integer[] findCheckItemIdsById(Integer id);

    void edit(CheckGroup checkGroup);

    void delRelation(Integer id);

    long findCountById(Integer id);

    void delById(Integer id);

    List<CheckGroup> findAll();

    List<CheckGroup> findCheckGroupsBySetmealId(Integer setmealId);
}
