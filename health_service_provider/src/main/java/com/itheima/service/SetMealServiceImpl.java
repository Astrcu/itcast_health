package com.itheima.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.constant.RedisConst;
import com.itheima.dao.SetMealDao;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.Setmeal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisPool;
import com.alibaba.fastjson.JSON;
import java.util.List;
import java.util.Map;

@Service
public class SetMealServiceImpl implements SetMealService {
    @Autowired
    SetMealDao setMealDao;
    @Autowired
    JedisPool jedisPool;
    @Override
    @Transactional
    //添加套餐
    public void add(Integer[] checkGroupIds, Setmeal setMeal) {
        setMealDao.add(setMeal);
        if (setMeal.getId() != null && checkGroupIds != null && checkGroupIds.length > 0) {
            for (Integer checkGroupId : checkGroupIds) {
                setMealDao.set(checkGroupId,setMeal.getId());
            }
            //清除redis
            jedisPool.getResource().del("setMeal");
        }
        jedisPool.getResource().sadd(RedisConst.SETMEAL_PIC_DB_RESOURCES,setMeal.getImg());
    }

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());

        //page的本质是一个集合
        Page<Setmeal> page = setMealDao.findByCondition(queryPageBean.getQueryString());

        return new PageResult(page.getTotal(),page);
    }
    //查询套餐
    @Override
    public List<Setmeal> getSetMeal() {
        List<Setmeal> setMealList=null;
        try {
            String setMeal = jedisPool.getResource().get("setMeal");
            if(setMeal==null || setMeal.equals("")){
                setMealList=setMealDao.getSetMeal();
                setMeal = JSONArray.toJSON(setMealList).toString();
                jedisPool.getResource().set("setMeal",setMeal);

            }
            else {
                setMealList = JSON.parseArray(setMeal, Setmeal.class);
            }
            jedisPool.getResource().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return setMealList;
    }
    //查询套餐详情setmealDetails
    @Override
    public Setmeal findDetailsById(Integer id) {
        Setmeal setmealOne=null;
        try {
            long setmealid = setMealDao.findDetailsById(id).getId();
            String setmealDetails = jedisPool.getResource().get("setmealDetails"+setmealid);

            //如果没有数据,到数据库查.
            //并将查询数据缓存到redis数据库。
            if(setmealDetails==null || setmealDetails.equals("")){
                setmealOne = setMealDao.findDetailsById(id);
                setmealDetails=JSON.toJSON(setmealOne).toString();
                jedisPool.getResource().set("setmealDetails"+setmealid,setmealDetails);

            }else {

                setmealOne = (Setmeal) JSON.parseObject(setmealDetails,Setmeal.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return setmealOne;


    }

    @Override
    public Setmeal findById(Integer id) {
        return setMealDao.findById(id);
    }

    @Override
    public List<Map<String, Object>> getSetmealReport() {
        return setMealDao.getSetmealReport();
    }
}
