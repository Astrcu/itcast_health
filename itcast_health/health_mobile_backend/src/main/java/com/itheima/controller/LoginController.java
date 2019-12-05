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

    @Autowired
    JedisPool jedisPool;

    @Reference
    MemberService memberService;

    @RequestMapping("/check")
    public Result check(@RequestBody Map<String,String> map){
        //获取手机号
        String telephone = map.get("telephone");
        //获取验证码
        String validateCodeInParam = map.get("validateCode");

        String validateCodeInRedis = jedisPool.getResource().get(RedisConst.SENDTYPE_LOGIN + "-" + telephone);
        //判断
        if(!(validateCodeInParam != null && validateCodeInParam.equals(validateCodeInRedis))){
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        }
        //判断是否是会员
        Member member = memberService.findByTelephone(telephone);
        if(member == null){
            //自动注册
            member = new Member();

            member.setPhoneNumber(telephone);
            member.setRegTime(new Date());

            //注册
            memberService.add(member);
        }
        return new Result(true,MessageConstant.LOGIN_SUCCESS);
    }
}
