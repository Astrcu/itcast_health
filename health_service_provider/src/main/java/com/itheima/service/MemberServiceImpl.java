package com.itheima.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.MemberDao;
import com.itheima.pojo.Member;
import com.itheima.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    MemberDao memberDao;

    @Override
    public Member findByTelephone(String telephone) {
        return memberDao.findByTel(telephone);
    }

    @Override
    public void add(Member member) {
        memberDao.add(member);
    }

    @Override
    public Map<String, Object> getMemberReport() {
        List<String> months = new ArrayList<>();
        //创建calender对象
        Calendar calendar = Calendar.getInstance();
        //回到十二个月以前
        calendar.add(Calendar.MONTH, -12);
        for (int i = 0; i < 12; i++) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
            //获取Date
            String str = sdf.format(calendar.getTime());
            months.add(str);
            calendar.add(Calendar.MONTH, 1);
        }

        //统计每月月底的时候，会员的数量
        List<Long> memberCount = new ArrayList<>();
        for (String month : months) {
            month += "-31";
            Long count = memberDao.findCountByMonth(month);
            memberCount.add(count);
        }

        Map<String, Object> map = new HashMap<>();
        map.put("months", months);
        map.put("memberCount", memberCount);
        return map;
    }

    /*会员总共12个
   [
     {"agePart":"0-18","ageCount":1},{"agePart":"18-30","ageCount":5},
     {"agePart":"30-45","ageCount":2},{"agePart":"45以上","ageCount":4}
   ]*/
    public List<Map<String, Object>> getMemberByAge() {
        //生日集合
        List<String> birthdayList = memberDao.getMemberByAge();
        //年龄集合
        List<Integer> ageList = new ArrayList<>();
        //今年
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        int nowYear = Integer.parseInt(sdf.format(new Date()));

        for (String birthday : birthdayList) {
            int memberYear = Integer.parseInt(birthday.substring(0, 4));
            Integer age = nowYear - memberYear;
            ageList.add(age);
        }//到这里ageList是有数据的
        int i = 0, j = 0, m = 0, n = 0;
        Map<String, Object> map1 = new HashMap<>();
        Map<String, Object> map2 = new HashMap<>();
        Map<String, Object> map3 = new HashMap<>();
        Map<String, Object> map4 = new HashMap<>();
        List<Map<String, Object>> list = new ArrayList<>();
        for (Integer age : ageList) {
            if (age > 0 && age <= 18) {
                i++;
            }
            map1.put("name", "0-18岁");
            map1.put("value", i);
        }
        for (Integer age : ageList) {
            if (age > 18 && age <= 30) {
                j++;
            }
            map2.put("name", "18-30岁");
            map2.put("value", j);
        }
        for (Integer age : ageList) {
            if (age > 30 && age <= 45) {
                m++;
            }
            map3.put("name", "30-45岁");
            map3.put("value", m);
        }
        for (Integer age : ageList) {
            if (age > 45) {
                n++;
            }
            map4.put("name", "大于45岁");
            map4.put("value", n);
        }
        list.add(map1);
        list.add(map2);
        list.add(map3);
        list.add(map4);
        return list;
    }

    @Override
    public List<Map<String, Object>> getMemberBySex() {

        List<Map<String, Object>> memberBySex = memberDao.getMemberBySex();
        for (Map<String, Object> map : memberBySex) {
            Object name = map.get("name");
            if (name != null && name.equals("0")) {
                name = "女";
                map.put("name",name);
            } else if (name != null && name.equals("1")) {
                name = "男";
                map.put("name",name);
            }
        }
        return memberBySex;
    }
	
	  /**
     * 获取会员统计数据  根据时间段范围
     * map
     *    months:['2019-12','2020-01','2020-02','2020-03']
     *    memberCount:[10,20,30,40]
     */
    @Override
    public Map<String, Object> getMemberReportByDate(List<Date> date) throws Exception {
        //获取前端传递过来的日期
        String startDate = DateUtils.parseDate2String(date.get(0), "yyyy-MM");
        String endDate = DateUtils.parseDate2String(date.get(1), "yyyy-MM");

        List<String> months = getMonthByDate(startDate, endDate);

        //统计每个月月底的会员数量
        List<Long> memberCount = new ArrayList<>();

        for (String month : months) {
            month += "-31";
            Long count = memberDao.findCountByMonth(month);
            memberCount.add(count);
        }

        //封装返回结果
        Map<String, Object> map = new HashMap<>();
        map.put("months", months);
        map.put("memberCount", memberCount);

        return map;
    }


    /**
     * 传递时间范围，返回范围内的每个月份
     * @param startDate "2019-01"
     * @param endDate  "2019-12"
     * @return
     * @throws Exception
     */
    public List<String> getMonthByDate(String startDate, String endDate) throws Exception {
        List<String> list = new ArrayList<>();

        Date d1 = new SimpleDateFormat("yyyy-MM").parse(startDate);//定义起始日期

        Date d2 = new SimpleDateFormat("yyyy-MM").parse(endDate);//定义结束日期

        Calendar dd = Calendar.getInstance();//定义日期实例

        dd.setTime(d1);//设置日期起始时间
        while (dd.getTime().before(d2) || dd.getTime().equals(d2)) {//判断是否到结束日期

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");

            String str = sdf.format(dd.getTime());

            System.out.println(str);//输出日期结果

            dd.add(Calendar.MONTH, 1);//进行当前日期月份加1
            list.add(str);
        }
        return list;

    }
}
