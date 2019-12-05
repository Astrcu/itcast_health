package com.itheima.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.constant.MessageConstant;
import com.itheima.dao.MemberDao;
import com.itheima.dao.OrderDao;
import com.itheima.dao.OrderSettingDao;
import com.itheima.entity.Result;
import com.itheima.pojo.Member;
import com.itheima.pojo.Order;
import com.itheima.pojo.OrderSetting;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderSettingDao orderSettingDao;

    @Autowired
    MemberDao memberDao;

    @Autowired
    OrderDao orderDao;

    @Override
    public Result addOrder(Map<String, String> map) throws Exception {

        //获取手机号码
        String telephone = map.get("telephone");
        //获取日期
        String orderDateStr = map.get("orderDate");
        //获取套餐id
        Integer setmealId =Integer.parseInt(map.get("setmealId"));

        //转换字符串日期为日期对象
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date orderDate = sdf.parse(orderDateStr);

        //判断是否进行了预约设置
        //根据日期查询预约设置对象
        OrderSetting orderSetting = orderSettingDao.findByOrderDate(orderDate);
        //如果对象为null  没有预约设置 返回不可预约
        if(orderSetting == null){
            return new Result(false, MessageConstant.SELECTED_DATE_CANNOT_ORDER);
        }
        //判断是否预约已满
        if(orderSetting.getReservations() >= orderSetting.getNumber()){
            return new Result(false,MessageConstant.ORDER_FULL);
        }
        //判断是否是会员
        //根据手机号查询会员
        Member member = memberDao.findByTelephone(telephone);
        //如果会员对象不为null 不是会员  自动注册为会员 继续预约
        if(member == null){
            member = new Member();

            //赋值
            member.setName(map.get("name"));
            member.setSex(map.get("sex"));
            member.setIdCard(map.get("idCard"));
            member.setPhoneNumber(map.get("telephone"));
            member.setRegTime(new Date());

            //注册会员(主键回显)
            memberDao.add(member);
        }else {
            //如果对象不为null  是会员  判断是否重复预约
            //以:会员id 日期 套餐id 为条件 查询预约信息 如果能查到 就是重复预约 返回不可重复预约
            //会员id
            Integer memberId = member.getId();
            //创建查询条件
            Order condition = new Order();
            condition.setMemberId(memberId);
            condition.setOrderDate(orderDate);
            condition.setSetmealId(setmealId);

            //查询预约对象
            long count = orderDao.findByOrder(condition);

            //判断
            if(count > 0){
                return new Result(false,MessageConstant.HAS_ORDERED);
            }
        }
        //没有查到 继续预约
        //添加预约信息到数据
        Order order = new Order();
        order.setMemberId(member.getId());
        order.setOrderDate(orderDate);
        order.setOrderType(map.get("orderType"));
        order.setOrderStatus(Order.ORDERSTATUS_NO);
        order.setSetmealId(setmealId);

        orderDao. addOrder(order);

        //已预约人数+1
        orderSetting.setReservations(orderSetting.getReservations() + 1);

        orderSettingDao.edit(orderSetting);
        return new Result(true,MessageConstant.ORDER_SUCCESS,order);
    }

    @Override
    public Map<String, Object> findById(Integer id) {
        return orderDao.findById(id);
    }


}
