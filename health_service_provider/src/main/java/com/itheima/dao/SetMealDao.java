package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.Setmeal;

import java.util.List;
import java.util.Map;

public interface SetMealDao {
    void add(Setmeal setMeal);

    void set(Integer checkGroupId, Integer id);

    Page<Setmeal> findByCondition(String queryString);

    List<Setmeal> getSetMeal();

    Setmeal findDetailsById(Integer id);

    Setmeal findById(Integer id);

    List<Map<String,Object>> getSetmealReport();

    List<Map<String,Object>> getHotSetmeal();
}
