package com.itheima.service;

import com.itheima.pojo.Member;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface MemberService {
    Member findByTelephone(String telephone);

    void add(Member member);

    Map<String,Object> getMemberReport();

    List<Map<String,Object>> getMemberByAge();

    List<Map<String, Object>> getMemberBySex();
	
	Map<String, Object> getMemberReportByDate(List<Date> date) throws Exception;

}
