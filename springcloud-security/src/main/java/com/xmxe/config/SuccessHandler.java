package com.xmxe.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录成功后的处理类
 *
 * 使用继承SavedRequestAwareAuthenticationSuccessHandler类的方式？
 * 因为SavedRequestAwareAuthenticationSuccessHandler这个类记住了你上一次的请求路径，比如：你请求user.html。
 * 然后被拦截到了登录页，这时候你输入完用户名密码点击登录，会自动跳转到user.html，而不是主页面。
 */
@Component
public class SuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
		//登录成功返回
//		Map<String, Object> paramMap = new HashMap<>();
//		paramMap.put("code", "200");
//		paramMap.put("message", "登录成功!");
//		//设置返回请求头
//		response.setContentType("application/json;charset=utf-8");
//		//写出流
//		PrintWriter out = response.getWriter();
//		out.write(JSONObject.toJSONString(paramMap));
//		out.flush();
//		out.close();

//		request.getSession().setAttribute("success",authentication.toString());
		super.onAuthenticationSuccess(request, response, authentication);
	}
}
