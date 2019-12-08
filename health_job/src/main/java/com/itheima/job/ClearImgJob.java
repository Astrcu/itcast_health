package com.itheima.job;

import com.itheima.constant.RedisConst;
import com.itheima.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisPool;

import java.util.Set;

public class ClearImgJob {

    @Autowired
    private JedisPool jedisPool;

    public void clear(){
        Set<String> imgNames = jedisPool.getResource().sdiff(RedisConst.SETMEAL_PIC_RESOURCES, RedisConst.SETMEAL_PIC_DB_RESOURCES);
        for (String imgName : imgNames) {
            QiniuUtils.deleteFileFromQiniu(imgName);
            jedisPool.getResource().srem(RedisConst.SETMEAL_PIC_RESOURCES,imgName);
            System.out.println("您删除了："+imgName);
        }
    }
}
