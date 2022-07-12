package com.xmxe.service;

import com.xmxe.entity.User;
import com.xmxe.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	UserMapper userMapper;
	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		User user = userMapper.loadUserByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("用户不存在");
		}
		user.setRoles(userMapper.getUserRoleByUid(user.getId()));
		return user;
	}
}