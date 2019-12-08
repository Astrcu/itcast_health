package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.Setmeal;

import java.util.List;
import java.util.Map;

public interface SetMealService {
    void add(Integer[] checkGroupIds, Setmeal setmeal);

    PageResult findPage(QueryPageBean queryPageBean);

    List<Setmeal> getSetMeal();

    Setmeal findDetailsById(Integer id);

    Setmeal findById(Integer id);

    List<Map<String,Object>> getSetmealReport();
}
