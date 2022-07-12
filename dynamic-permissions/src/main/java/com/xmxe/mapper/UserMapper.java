package com.xmxe.mapper;

import com.xmxe.entity.Role;
import com.xmxe.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
	List<Role> getUserRoleByUid(Integer uid);
	User loadUserByUsername(String username);
}