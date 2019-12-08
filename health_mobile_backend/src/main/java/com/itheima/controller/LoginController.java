package com.itheima.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisConst;
import com.itheima.entity.Result;
import com.itheima.pojo.Member;
import com.itheima.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Reference
    MemberService memberService;

    @Autowired
    JedisPool jedisPool;


    @RequestMapping("/check")
    public Result check(@RequestBody Map<String, String> map) {
        String telephone = map.get("telephone");
        String validateCodeInParam = map.get("validateCode");

        String validateCodeInRedis = jedisPool.getResource().get(RedisConst.SENDTYPE_LOGIN + "-" + telephone);
        if (!(validateCodeInParam != null && validateCodeInParam.equals(validateCodeInRedis))) {
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        }
        Member member = memberService.findByTelephone(telephone);
        if (member == null) {
            member = new Member();
            member.setRegTime(new Date());
            member.setPhoneNumber(telephone);
            memberService.add(member);
        }
        return new Result(true,MessageConstant.LOGIN_SUCCESS);
    }
}

