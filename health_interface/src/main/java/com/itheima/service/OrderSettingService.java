package com.itheima.service;

import com.itheima.pojo.OrderSetting;

import java.util.List;

public interface OrderSettingService {
    void addOrderSettings(List<OrderSetting> orderSettingList);

    List<OrderSetting> findByMonth(String month);

    void deleteByDate(String today);
}
