package com.itheima.dao;

import com.itheima.pojo.OrderSetting;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface OrderSettingDao {

    OrderSetting findByOrderDate(Date orderDate);

    void add(OrderSetting orderSetting);

    List<OrderSetting> findByBetweenDate(Map<String, String> map);

    void edit(OrderSetting orderSetting);
}
