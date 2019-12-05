package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.Result;
import com.itheima.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/user")
public class UserController {

   @Reference(timeout = 50000)
   UserService userService;

   @RequestMapping("/doSuccess")
   public String doSuccess(){
    return "redirect:http://localhost:83/pages/main.html";
   }

    @RequestMapping("/doFail")
    public String doFail(){
        return "redirect:http://localhost:83/login.html?message=loginFail";
    }

    @RequestMapping("/getUsername")
    @ResponseBody
    public Result getUsername(){
       try {
           //使用帮助类获取安全框架上下文对象
           SecurityContext context = SecurityContextHolder.getContext();
           //获取认证对象
           Authentication authentication = context.getAuthentication();
           //获取重要信息
           Object principal = authentication.getPrincipal();
           //转换为user类型
           User user = (User) principal;
           //获取username
           String username = user.getUsername();
           return new Result(true, MessageConstant.GET_USERNAME_SUCCESS,username);
       }catch (Exception e){
           e.printStackTrace();
           return new Result(false,MessageConstant.GET_USERNAME_FAIL);
       }
    }
}
