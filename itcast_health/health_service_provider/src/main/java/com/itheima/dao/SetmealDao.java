package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.Setmeal;

import java.util.List;
import java.util.Map;

public interface SetmealDao {
    void add(Setmeal setmeal);

    void set(Integer setmealId, Integer checkgroupId);

    Page<Setmeal> findByCondition(String queryString);

    List<Setmeal> getSetmeal();

    Setmeal findDetailsById(Integer id);

    Setmeal findById(Integer id);

    List<Map<String, Object>> getSetmealReport();

    List<Map<String, Object>> getHotSetmeal();

}
