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
            //调用工具类生成验证码
            Integer validateCode = ValidateCodeUtils.generateValidateCode(6);

            //发送验证码到手机
            SMSUtils.sendShortMessage(telephone,String.valueOf(validateCode));

            //存储验证码到redis
            jedisPool.getResource().setex(RedisConst.SENDTYPE_ORDER + "-" + telephone,5*60,String.valueOf(validateCode));
            return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
        }
    }

    @RequestMapping("/send4Login")
    public Result send4Login(String telephone){
        try {
            //调用工具类生成验证码
            Integer validateCode = ValidateCodeUtils.generateValidateCode(6);

            //发送验证码到手机
            SMSUtils.sendShortMessage(telephone,String.valueOf(validateCode));

            //存储验证码到redis
            jedisPool.getResource().setex(RedisConst.SENDTYPE_LOGIN + "-" + telephone,5*60,String.valueOf(validateCode));
            return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
        }
    }
}
