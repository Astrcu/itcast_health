package com.itheima.dao;

import com.itheima.pojo.Member;

import java.util.List;
import java.util.Map;

public interface MemberDao {

    Member findByTel(String telephone);

    void add(Member member);

    long findCountByMonth(String month);

    long findTodayNewMember(String todayDate);

    long findTotalCount();

    long findCountByAfterDate(String date);

    List<String> getMemberByAge();

    List<Map<String,Object>> getMemberBySex();
}
