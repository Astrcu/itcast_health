package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisConst;
import com.itheima.entity.Result;
import com.itheima.pojo.Order;
import com.itheima.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Reference
    OrderService orderService;

    @Autowired
    JedisPool jedisPool;

    @RequestMapping("/submit")
    public Result submit(@RequestBody Map<String,String> map){//map就包含了套餐预约的所有参数信息

        //获取参数中的手机号码
        String telephone = map.get("telephone");
        //校验验证码 如果正确 就提交 如果不正确 返回验证码弹出错误
        //获取reids中的验证码
        String validateCodeInRedis = jedisPool.getResource().get(RedisConst.SENDTYPE_ORDER + "-" + telephone);
        System.out.println(validateCodeInRedis);
        //获取参数中的验证码
        String validateCodeInParam = map.get("validateCode");
        System.out.println(validateCodeInParam);

        //判断
        if(!(validateCodeInParam!=null && validateCodeInParam.equals(validateCodeInRedis))){
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        }

        //设置预约的方式
        map.put("orderType", Order.ORDERTYPE_WEIXIN);

        //调用service 添加预约信息
        Result result = null;
        try {
            result = orderService.addOrder(map);
        }catch (Exception e){
            e.printStackTrace();
        }

        //预约成功 发送预约成功的短信通知给用户
        if(result.isFlag()){

        }
        return result;
    }

    @RequestMapping("/findById")
    public Result findById(Integer id){
        try {
            Map<String,Object> map = orderService.findById(id);//map包含了order中的参数信息

            //将时间的毫秒值转换为字符串
            Date date =(Date) map.get("orderDate");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String orderDate = sdf.format(date);
            map.put("orderDate",orderDate);

            return new Result(true,MessageConstant.QUERY_ORDER_SUCCESS,map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_ORDER_FAIL);
        }

    }
}
