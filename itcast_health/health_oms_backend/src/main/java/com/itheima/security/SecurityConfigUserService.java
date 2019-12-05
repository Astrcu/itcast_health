package com.itheima.security;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.pojo.Permission;
import com.itheima.pojo.Role;
import com.itheima.pojo.SysUser;
import com.itheima.service.UserService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class SecurityConfigUserService implements UserDetailsService {

    @Reference(timeout = 50000)
    UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //根据用户名 从数据库获取用户信息 包括角色 和 权限
        SysUser sysUser = userService.findByUsername(username);

        if(sysUser != null){
            //创建权限集合
            List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
            //遍历得到每个角色
            for (Role role : sysUser.getRoles()) {
                //遍历角色 得到权限
                for (Permission permission : role.getPermissions()) {
                    //创建权限对象  添加到集合中
                    SimpleGrantedAuthority authority = new SimpleGrantedAuthority(permission.getKeyword());
                    authorityList.add(authority);
                }
            }
            //封装UserDetails并返回
            UserDetails userDetails = new User(sysUser.getUsername(), sysUser.getPassword(), authorityList);
            return userDetails;
        }
        return null;
    }
}
