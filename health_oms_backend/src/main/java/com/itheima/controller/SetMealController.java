package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisConst;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.Setmeal;
import com.itheima.service.SetMealService;
import com.itheima.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/setMeal")
public class SetMealController {

    @Reference
    SetMealService setMealService;
    @Autowired
    JedisPool jedisPool;


    @RequestMapping("/upload")
    public Result upload(MultipartFile imgFile){
        String uuid = UUID.randomUUID().toString().replace("-", "");
        //获取原始文件名
        String originalFilename = imgFile.getOriginalFilename();
        //获取原始文件名的后缀
        String extendName = originalFilename.substring(originalFilename.lastIndexOf("."));
        //上传时候的名字
        String serverName = uuid+extendName;
        try {
            //上传文件到七牛云
            QiniuUtils.upload2Qiniu(imgFile.getBytes(),serverName);
            //上传文件到redis
            jedisPool.getResource().sadd(RedisConst.SETMEAL_PIC_RESOURCES,serverName);
            return new Result(true,MessageConstant.PIC_UPLOAD_SUCCESS,serverName);
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(true,MessageConstant.PIC_UPLOAD_FAIL);
        }
    }
    @RequestMapping("/add")
    public Result add(@RequestBody Map<String,Object> map){
        //json对象转换pojo对象
        JSONObject jsonObject = (JSONObject) map.get("setMeal");
        Setmeal setMeal = jsonObject.toJavaObject(Setmeal.class);

        //json数组转换普通数组
        JSONArray jsonArray = (JSONArray) map.get("checkGroupIds");
        Integer[] checkGroupIds = jsonArray.toArray(new Integer[]{});

        try {
            setMealService.add(checkGroupIds,setMeal);
            return new Result(true,MessageConstant.ADD_SETMEAL_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.ADD_SETMEAL_FAIL);
        }
    }
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        return setMealService.findPage(queryPageBean);
    }
}
