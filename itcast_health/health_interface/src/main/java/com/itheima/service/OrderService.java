package com.itheima.service;

import com.itheima.entity.Result;

import java.text.ParseException;
import java.util.Map;

public interface OrderService {
    Result addOrder(Map<String, String> map) throws Exception;

    Map<String, Object> findById(Integer id);
}
