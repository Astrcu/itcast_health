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
    OrderDao orderDao;
    @Autowired
    OrderSettingDao orderSettingDao;

    @Autowired
    MemberDao memberDao;

    /**
     * 步骤：
     * 1.判断是否进行了预约设置
     * 根据日期查询预约设置对象
     * 如果对象为null， 没有预约设置， 返回不可预约
     * 如果对象不为null, 继续
     * 2. 判断是否预约已满
     * 如果已预约大于等于可预约，预约已满，返回已满
     * 如果已预约小于可预约，继续
     * 3. 判断是否是会员
     * 根据手机号码查询会员对象(bug：一个人可以使用不同的手机号预约服务)
     * 如果会员对象为null， 不是会员，自动注册为会员， 继续预约
     * 如果会员对象不为null, 是会员，判断是否重复预约
     *      重复预约：同一个人预约了同一天同一个套餐
     *      以：会员id，日期，套餐id 为条件，查询预约信息，如果能查到，重复预约，返回不可重复预约
     * 如果没有查到，继续预约
     * 4. 开始预约
     * 添加预约信息到数据
     * 5. 已预约人数加 1
     *
     * @param map
     * @return
     */
    @Override
    public Result addOrder(Map<String, String> map) throws Exception {

        String telephone = map.get("telephone");
        String orderDateStr = map.get("orderDate");
        String setmealId = map.get("setmealId");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date orderDate = sdf.parse(orderDateStr);
        //1.
        OrderSetting orderSetting = orderSettingDao.findByOrderDate(orderDate);
        if (orderSetting == null) {
            return new Result(false, MessageConstant.SELECTED_DATE_CANNOT_ORDER);
        }
        //2.
        if (orderSetting.getReservations() >= orderSetting.getNumber()) {
            return new Result(false, MessageConstant.ORDER_FULL);
        }
        //3.
        Member member = memberDao.findByTel(telephone);//TODo 1
        if (member == null) {
            member = new Member();
            member.setName(map.get("name"));
            member.setSex(map.get("sex"));
            member.setIdCard(map.get("idCard"));
            member.setPhoneNumber(map.get("telephone"));
            member.setRegTime(new Date());
            //主键回显
            memberDao.add(member);//TODO 2
        } else {
            Integer id = member.getId();
            Order order = new Order();
            order.setMemberId(id);
            order.setOrderDate(orderDate);
            order.setSetmealId(Integer.parseInt(setmealId));

            Long count = orderDao.findByOrder(order);//TODO 3
            if (count > 0) {
                return new Result(false, MessageConstant.HAS_ORDERED);
            }
        }
        //4.
        Order order = new Order();
        //member只是地址值，在堆内存，可以进行赋值
        order.setMemberId(member.getId());
        order.setOrderDate(orderDate);
        order.setOrderType(map.get("orderType"));
        order.setOrderStatus(Order.ORDERSTATUS_NO);
        order.setSetmealId(Integer.parseInt(setmealId));

        orderDao.addOrder(order);//TODO 4
        //5.
        orderSetting.setReservations(orderSetting.getReservations() + 1);
        orderSettingDao.edit(orderSetting);//TODO 5

        return new Result(true, MessageConstant.ORDER_SUCCESS, order);
    }

    @Override
    public Map<String, Object> findById(Integer id) {
        return orderDao.findById(id);
    }
}
