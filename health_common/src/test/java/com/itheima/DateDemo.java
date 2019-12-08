package com.itheima;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DateDemo {

    public static void main(String[] args) {
        List<String> months = new ArrayList<>();
        //创建calender对象
        Calendar calendar = Calendar.getInstance();
        //回到十二个月以前

        calendar.add(Calendar.MONTH,-12);
        for (int i = 0; i < 12; i++) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            //获取Date
            String str = sdf.format(calendar.getTime());
            months.add(str);
            calendar.add(Calendar.MONTH,1);
        }

    }
}
