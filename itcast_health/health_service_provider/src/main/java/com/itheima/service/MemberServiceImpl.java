package com.itheima.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.MemberDao;
import com.itheima.pojo.Member;
import org.springframework.beans.factory.annotation.Autowired;
import sun.misc.Cleaner;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class MemberServiceImpl implements MemberService{

    @Autowired
    MemberDao memberDao;

    @Override
    public Member findByTelephone(String telephone) {
        return memberDao.findByTelephone(telephone);
    }

    @Override
    public void add(Member member) {
        memberDao.add(member);
    }

    @Override
    public Map<String, Object> getMemberReport() {
        //最近一年十二个月份
        List<String> months = new ArrayList<>();
        //获取日历
        Calendar calendar = Calendar.getInstance();
        //返回12个月以前
        calendar.add(Calendar.MONTH,-12);
        for (int i = 0; i < 12; i++) {
            //转换为字符串格式
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
            String str = sdf.format(calendar.getTime());
            months.add(str);

            //向前一个月
            calendar.add(Calendar.MONTH,1);
        }

        //统计每月月底时候的会员总数量
        List<Long> memberCount = new ArrayList<>();
        //每一个月
        for (String month : months) {
            month += "-31";
            long count = memberDao.findCountByMonth(month);
            memberCount.add(count);
        }
        //创建map集合
        Map<String, Object> map = new HashMap<>();
        map.put("months",months);
        map.put("memberCount",memberCount);
        return map;
    }
}
