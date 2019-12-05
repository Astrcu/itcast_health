package com.itheima.dao;

import com.itheima.pojo.Order;

import java.util.Map;

public interface OrderDao {

    long findByOrder(Order condition);

    void addOrder(Order order);

    Map<String, Object> findById(Integer id);

    long findTodayOrderNumber(String reportDate);

    long findTodayVisitsNumber(String reportDate);

    long findOrderNumberByBetweenDate(String thisWeekMonday, String thisWeekSunday);

    long findVisitsNumberByAfterDate(String thisWeekMonday);

}
