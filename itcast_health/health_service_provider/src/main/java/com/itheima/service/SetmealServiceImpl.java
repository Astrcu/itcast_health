package com.itheima.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.constant.RedisConst;
import com.itheima.dao.SetmealDao;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.Setmeal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisPool;

import java.util.List;
import java.util.Map;

@Service(timeout = 50000)
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    SetmealDao setmealDao;
    @Autowired
    JedisPool jedisPool;

    @Override
    @Transactional
    public void add(Integer[] checkgroupIds, Setmeal setmeal) {
        //添加套餐 主键回显
        setmealDao.add(setmeal);
        //维护套餐与检查组的关系
        if(setmeal.getId()!=null && checkgroupIds!=null && checkgroupIds.length>0){
            for (Integer checkgroupId : checkgroupIds) {
                setmealDao.set(setmeal.getId(),checkgroupId);
            }
        }
        //添加套餐完成  图片与数据库之间就存在了联系
        //Redis Sadd 命令将一个或多个成员元素加入到集合中，已经存在于集合的成员元素将被忽略
        jedisPool.getResource().sadd(RedisConst.SETMEAL_PIC_DB_RESOURCES,setmeal.getImg());
    }

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        //开始分页
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        //条件查询
        Page<Setmeal> page = setmealDao.findByCondition(queryPageBean.getQueryString());
        //创建返回值对象
        return new PageResult(page.getTotal(),page);
    }

    @Override
    public List<Setmeal> getSetmeal() {
        return setmealDao.getSetmeal();
    }

    @Override
    public Setmeal findDetailsById(Integer id) {
        return setmealDao.findDetailsById(id);
    }

    @Override
    public Setmeal findById(Integer id) {
        return setmealDao.findById(id);
    }

    @Override
    public List<Map<String, Object>> getSetmealReport() {
        return setmealDao.getSetmealReport();
    }
}
