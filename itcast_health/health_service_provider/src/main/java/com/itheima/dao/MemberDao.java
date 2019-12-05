package com.itheima.dao;

import com.itheima.pojo.Member;

public interface MemberDao {

    Member findByTelephone(String telephone);


    void add(Member member);

    long findCountByMonth(String month);

    long findTodayNewMember(String reportDate);

    long findTotalCount();

    long findCountByAfterDate(String thisWeekMonday);
}
