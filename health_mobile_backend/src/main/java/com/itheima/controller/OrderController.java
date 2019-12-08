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

    @Autowired
    JedisPool jedisPool;
    @Reference
    OrderService orderService;
    /**
     * 步骤：
     *      1. 校验验证码，如果正确，提交，如果错误，返回验证码输出错误
     *      2. 设置预约方式
     *      3. 调用service，添加预约信息
     *      4. 预约成功后，发送预约成功的通知短信给用户
     * @param map
     * @return
     */
    @RequestMapping("/submit")
    public Result submit(@RequestBody Map<String,String> map){
        String telephone = map.get("telephone");
        //获取redis中的验证码
        String validateCodeInRedis = jedisPool.getResource().get(RedisConst.SENDTYPE_ORDER + "-" + telephone);
        //获取输入的验证码
        String validateCodeInParam = map.get("validateCode");
        if(!(validateCodeInParam!=null && validateCodeInParam.equals(validateCodeInRedis))){
            return new Result(false,MessageConstant.VALIDATECODE_ERROR);
        }
        //设置预约方式
        map.put("orderType",Order.ORDERTYPE_WEIXIN);
        //添加预约信息
        Result result = null;
        try {
            result = orderService.addOrder(map);

        } catch (Exception e) {
            e.printStackTrace();
        }
        if (result.isFlag()){
            //预约成功发送成功短信，暂时还没有这个功能
        }

        return result;
    }

    @RequestMapping("/findById")
    public Result findById(Integer id){
        try {
            Map<String,Object> map = orderService.findById(id);
            Date date = (Date)map.get("orderDate");
            System.out.println(date);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String orderDate = sdf.format(date);
            System.out.println(orderDate);
            map.put("orderDate",orderDate);
            return new Result(true,MessageConstant.QUERY_ORDER_SUCCESS,map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_ORDER_FAIL);
        }
    }
}
