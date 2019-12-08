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

    @Reference
    UserService userService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser = userService.findByUsername(username);
        if(sysUser!=null){
            List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
            for (Role role : sysUser.getRoles()) {
                for (Permission permission : role.getPermissions()) {
                    //创建权限对象 keyword权限关键字
                    SimpleGrantedAuthority grantedAuthority = new SimpleGrantedAuthority(permission.getKeyword());
                    authorityList.add(grantedAuthority);
                }
            }
            User user = new User(sysUser.getUsername(),sysUser.getPassword(),authorityList);
            return user;
        }
        return null;
    }
}
