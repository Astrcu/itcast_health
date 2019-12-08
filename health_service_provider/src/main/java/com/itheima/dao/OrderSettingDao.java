package com.itheima.dao;

import com.itheima.pojo.OrderSetting;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface OrderSettingDao {
    OrderSetting findByOrderDate(Date orderDate);

    void add(OrderSetting orderSetting);

    void edit(OrderSetting orderSetting);


    List<OrderSetting> findByMonth(Map<String,String> map);

    void deleteByDate(String today);
}
