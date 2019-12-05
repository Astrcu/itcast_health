package com.itheima.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.OrderSettingDao;
import com.itheima.pojo.OrderSetting;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderSettingServiceImpl implements OrderSettingService {

    @Autowired
    OrderSettingDao orderSettingDao;

    @Override
    public void addOrderSettings(List<OrderSetting> orderSettingList) {
        if(orderSettingList!=null && orderSettingList.size()>0){
            for (OrderSetting orderSetting : orderSettingList) {
                //判断该日期是否进行过预约设置
                OrderSetting orderSettingDb = orderSettingDao.findByOrderDate(orderSetting.getOrderDate());
                //如果没有 直接添加
                if(orderSettingDb == null){
                    orderSettingDao.add(orderSetting);
                }else {
                    //如果存在  修改之前判断已预约是否大于可预约
                    if(orderSettingDb.getReservations() > orderSetting.getNumber()){
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        throw new RuntimeException("{" + sdf.format(orderSetting.getOrderDate()) + "}已预约大于可预约,不能设置!!!");
                    }else {
                        //执行修改
                        orderSettingDao.edit(orderSetting);
                    }
                }
            }
        }
    }

    @Override
    public List<OrderSetting> findByMonth(String month) {
        String startDate = month + "-01";
        String endDate = month + "-31";

        Map<String, String> map = new HashMap<>();
        map.put("startDate",startDate);
        map.put("endDate",endDate);
        return orderSettingDao.findByBetweenDate(map);
    }
}
