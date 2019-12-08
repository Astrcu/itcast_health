package com.itheima.dao;

import com.itheima.pojo.Order;

import java.util.Map;

public interface OrderDao {
    void addOrder(Order order);

    long findByOrder(Order order);

    Map<String,Object> findById(Integer id);

    long findTodayOrderNumber(String todayDate);

    long findTodayVisitsNumber(String todayDate);

    long findOrderNumberByBetweenDate(String thisWeekMonday, String thisWeekSunday);

    long findVisitsNumberByAfterDate(String date);
}
