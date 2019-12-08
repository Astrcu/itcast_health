package com.itheima.job;

import com.alibaba.dubbo.config.annotation.Reference;

import com.itheima.service.OrderSettingService;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class ClearOrderJob {

    @Reference
    OrderSettingService orderSettingService;

    //定时清理预约设置历史数据
    public void clear(){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        Date today=cal.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String today1 = sdf.format(today);
        orderSettingService.deleteByDate(today1);
    }
}
