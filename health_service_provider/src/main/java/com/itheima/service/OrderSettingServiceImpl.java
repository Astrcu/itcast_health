package com.itheima.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.OrderSettingDao;
import com.itheima.pojo.OrderSetting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//每日预约订单
@Service
public class OrderSettingServiceImpl implements OrderSettingService {

    @Autowired
    OrderSettingDao orderSettingDao;

    /**
     * 步骤:
     * 1. 循环
     * 2.判断该日期是否进行过预约设置
     *      2.1 如果没有，直接添加
     *      2.2 如果存在
     *          2.2.1 修改之前，要判断已预约是否大于可预约
     *          2.2.2 执行修改
     *
     * @param orderSettingList
     */
    @Override
    @Transactional
    public void addOrderSettings(List<OrderSetting> orderSettingList) {
        if (orderSettingList != null && orderSettingList.size() > 0) {
            for (OrderSetting orderSetting : orderSettingList) {
                OrderSetting orderSettingDB = orderSettingDao.findByOrderDate(orderSetting.getOrderDate());
                //如果没有直接添加
                if (orderSettingDB == null) {
                    orderSettingDao.add(orderSetting);
                } else {
                    if (orderSettingDB.getReservations() > orderSetting.getNumber()) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        throw new RuntimeException("[" + sdf.format(orderSetting.getOrderDate()) + "]'已预约大于可预约，不能设置");
                    } else {
                        orderSettingDao.edit(orderSetting);
                    }
                }
            }
        }
    }

    @Override
    public List<OrderSetting> findByMonth(String month) {
        String startDate = month+"-01";
        String endDate = month+"-31";

        Map<String,String> map = new HashMap<>();

        map.put("startDate",startDate);
        map.put("endDate",endDate);

        return orderSettingDao.findByMonth(map);
    }

    @Override
    public void deleteByDate(String today) {
        orderSettingDao.deleteByDate(today);
    }
}
