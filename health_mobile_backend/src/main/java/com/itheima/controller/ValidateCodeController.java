package com.itheima.controller;


import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisConst;
import com.itheima.entity.Result;
import com.itheima.utils.SMSUtils;
import com.itheima.utils.ValidateCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

@RestController
@RequestMapping("/validateCode")
public class ValidateCodeController {

    @Autowired
    JedisPool jedisPool;
    @RequestMapping("/send4Order")
    public Result send4Order(String telephone){
        try {
            //生成随机验证码
            Integer validateCode = ValidateCodeUtils.generateValidateCode(6);
            //发送短信
            System.out.println("验证码是："+validateCode);
            SMSUtils.sendShortMessage(telephone,validateCode);
            //存储验证码到redis
            jedisPool.getResource().setex(RedisConst.SENDTYPE_ORDER+"-"+telephone,5*60,String.valueOf(validateCode));

            return new Result(true,MessageConstant.SEND_VALIDATECODE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true,MessageConstant.SEND_VALIDATECODE_FAIL);
        }
    }

    @RequestMapping("/send4Login")
    public Result send4Login(String telephone){
        try {
            Integer validateCode = ValidateCodeUtils.generateValidateCode(6);
            //SMSUtils.sendShortMessage(telephone,validateCode);
            jedisPool.getResource().setex(RedisConst.SENDTYPE_LOGIN+"-"+telephone,5*60,String.valueOf(validateCode));
            return new Result(true,MessageConstant.SEND_VALIDATECODE_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.SEND_VALIDATECODE_FAIL);
        }
    }
}
