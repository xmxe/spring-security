package com.xmxe.service;

import com.xmxe.dao.LoginDao;
import com.xmxe.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 数据库交互接口
 */
@Service
public class LoginService {
    private final LoginDao loginDao;

    @Autowired
    public LoginService(LoginDao loginDao) {
        this.loginDao = loginDao;
    }

    /**
     * 根据用户名获取用户信息
     */
    public List<User> getUserByUsername(String username) {
        return loginDao.getUserByUsername(username);
    }

    /**
     * 根据用户名获取权限信息
     */
    public List<String> getPermissionsByUsername(String username) {
        return loginDao.getPermissionsByUsername(username);
    }

    /**
     * 根据用户名获取角色信息
     */
    public List<String> getRoleByUsername(String username) {
        return loginDao.getRoleByUsername(username);
    }
}