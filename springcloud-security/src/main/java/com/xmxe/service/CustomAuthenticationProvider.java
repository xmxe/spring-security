package com.xmxe.service;

import com.xmxe.entity.GrantedAuthorityImpl;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;

/**
 * 自定义身份认证验证组件
 * 这个类就是提供密码验证功能，在实际使用时换成自己相应的验证逻辑，从数据库中取出、比对、赋予用户相应权限。
 */
public class CustomAuthenticationProvider implements AuthenticationProvider {

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		// 获取输入的未认证的用户名 & 密码
		String name = authentication.getName();
		String password = authentication.getCredentials().toString();

		// 认证逻辑
		if (name.equals("admin") && password.equals("123456")) {
			// 这里设置权限和角色
			ArrayList<GrantedAuthority> authorities = new ArrayList<>();
			authorities.add( new GrantedAuthorityImpl("ROLE_ADMIN") );
			authorities.add( new GrantedAuthorityImpl("AUTH_WRITE") );
			// 生成令牌
			Authentication authentication1 = new UsernamePasswordAuthenticationToken(name, password, authorities);
			return authentication1;
		}else {
			throw new BadCredentialsException("密码错误~");
		}
	}

	// 是否可以提供输入类型的认证服务
	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
}
