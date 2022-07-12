package com.xmxe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@EnableDiscoveryClient
@SpringBootApplication
public class SecurityCloudApplication {

	public static void main(String[] args) {

		SpringApplication.run(SecurityCloudApplication.class, args);

		// 测试spring security密码加密
		// testPasswordEncoder();
	}

	/*public static void testPasswordEncoder(){
		String password = "guest";//自定义一个密码
		PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		String passwordAfterEncoder = passwordEncoder.encode(password);//对用户密码进行加密
		System.out.println(passwordAfterEncoder);
		System.out.println(passwordEncoder.matches("admin",passwordAfterEncoder));
	}*/
}

