package com.xmxe.service;

import com.xmxe.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * Spring Security进行用户认证时，需要根据用户的账号、密码、权限等信息进行认证，
 * 因此，需要根据查询到的用户信息封装成一个认证用户对象并交给Spring Security进行认证。
 * 查询用户输入的账号所对应的 正确 的密码和角色等信息并封装成UserDetails对象，然后返回给AthenticationProvider进行认证，
 * AthenticationProvider的作用即是认证用户的密码等认证信息是否正确
 */
@Service
public class MyUserDetailService implements UserDetailsService {
    @Autowired
    LoginService loginService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<User> users = loginService.getUserByUsername(username);
        if (users == null || users.size() == 0) {
            throw new UsernameNotFoundException("用户名未找到");
        }
        // 获得用户真正的密码进行前端用户的匹配
        String password = users.get(0).getPassword();
//            PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
//            String passwordAfterEncoder = passwordEncoder.encode(password);//对用户密码进行加密
//            System.out.println(username + "/" + passwordAfterEncoder);

        // 获取用户真正的角色信息
        List<String> roles = loginService.getRoleByUsername(username);
        // 获取用户真正的权限信息
        List<String> permissions = loginService.getPermissionsByUsername(username);
        // 将用户拥有的权限放入数组
        String[] permissionArr = new String[roles.size() + permissions.size()];
        int permissionArrIndex = 0;

        for (String role : roles) {
            permissionArr[permissionArrIndex] = "ROLE_" + role;
            permissionArrIndex++;
        }
        for (String permission : permissions) {
            permissionArr[permissionArrIndex] = permission;
            permissionArrIndex++;
        }
        System.out.println("UserDetailsService中的permissionArr============" + Arrays.toString(permissionArr));
        return org.springframework.security.core.userdetails.User.withUsername(username).password(password).authorities(permissionArr).build();
    }
}
