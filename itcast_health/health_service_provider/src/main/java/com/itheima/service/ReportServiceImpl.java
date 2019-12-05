package com.itheima.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.MemberDao;
import com.itheima.dao.OrderDao;
import com.itheima.dao.SetmealDao;
import com.itheima.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    MemberDao memberDao;

    @Autowired
    OrderDao orderDao;

    @Autowired
    SetmealDao setmealDao;

    @Override
    public Map<String, Object> getBusinessReportData() throws Exception {
        //获取当前时间
        String todayDate = DateUtils.parseDate2String(new Date());
        //获取本周一的日期
        String thisWeekMonday = DateUtils.parseDate2String(DateUtils.getThisWeekMonday());
        //获取本周日的日期
        String thisWeekSunday = DateUtils.parseDate2String(DateUtils.getSundayOfThisWeek());
        //获取本月1号 日期
        String thisMonthFirstDay = DateUtils.parseDate2String(DateUtils.getFirstDay4ThisMonth());
        //获取本月最后一天
        String thisMonthLastDay = DateUtils.parseDate2String(DateUtils.getLastDay4ThisMonth());
        //reportDate:null  统计时间(当前时间 今天)
        String reportDate = todayDate;
        //todayNewMember :0,  今日新增会员数（注册时间为今天）
        long todayNewMember = memberDao.findTodayNewMember(reportDate);
        //totalMember :0,     总会员数（）
        long totalMember = memberDao.findTotalCount();
        //thisWeekNewMember :0,  本周新增会员数( 注册时间 在周一以后即可)
        long thisWeekNewMember = memberDao.findCountByAfterDate(thisWeekMonday);
        //thisMonthNewMember :0,  本月新增会员数( 注册时间 在1号以后即可)
        long thisMonthNewMember = memberDao.findCountByAfterDate(thisMonthFirstDay);
        //todayOrderNumber :0,   今日预约数（今天有多少人预约要来体检（今日应该来多少人））
        long todayOrderNumber = orderDao.findTodayOrderNumber(reportDate);
        //todayVisitsNumber :0,   今日到诊数（今日实际来了多少人）
        long todayVisitsNumber = orderDao.findTodayVisitsNumber(reportDate);
        //thisWeekOrderNumber :0,   本周预约数（预约本周要来的人数（预约本周一到周日之间要来的人数））
        long thisWeekOrderNumber =  orderDao.findOrderNumberByBetweenDate(thisWeekMonday, thisWeekSunday);
        //thisWeekVisitsNumber :0,  本周到诊数（本周实际来了多少人（周一后实际到诊人数））
        long thisWeekVisitsNumber = orderDao.findVisitsNumberByAfterDate(thisWeekMonday);
        //thisMonthOrderNumber :0,  本月预约数（预约本月要来的人数，预约1号到本月最后一天之间要来的人数））
        long thisMonthOrderNumber = orderDao.findOrderNumberByBetweenDate(thisMonthFirstDay, thisMonthLastDay);
        //thisMonthVisitsNumber :0,  本月到诊数（本月实际来了多少人（1号以后实际到诊人数））
        long thisMonthVisitsNumber = orderDao.findVisitsNumberByAfterDate(thisMonthFirstDay);
        //hotSetmeal :[   热门套餐(前三)
        //     {name:'阳光爸妈升级肿瘤12项筛查（男女单人）体检套餐',setmeal_count:200,proportion:0.222},
        //    {name:'阳光爸妈升级肿瘤12项筛查体检套餐',setmeal_count:200,proportion:0.222}
        //  ]
        List<Map<String,Object>> hotSetmeal =  setmealDao.getHotSetmeal();

        //创建返回值map
        Map<String,Object> map = new HashMap<>();
        map.put("reportDate", reportDate);
        map.put("totalMember", totalMember);
        map.put("todayNewMember", todayNewMember);
        map.put("thisWeekNewMember", thisWeekNewMember);
        map.put("thisMonthNewMember", thisMonthNewMember);
        map.put("todayOrderNumber", todayOrderNumber);
        map.put("todayVisitsNumber", todayVisitsNumber);
        map.put("thisWeekOrderNumber", thisWeekOrderNumber);
        map.put("thisWeekVisitsNumber", thisWeekVisitsNumber);
        map.put("thisMonthOrderNumber", thisMonthOrderNumber);
        map.put("thisMonthVisitsNumber", thisMonthVisitsNumber);
        map.put("hotSetmeal", hotSetmeal);

        return map;
    }
}
