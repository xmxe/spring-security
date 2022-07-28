package com.xmxe.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * OncePerRequestFilter类实际上是一个实现了Filter接口的抽象类。spring对Filter进行了一些封装处理，OncePerRequestFilter是在一次外部请求中只过滤一次。
 * 对于服务器内部之间的forward等请求，不会再次执行过滤方法。实现Filter接口，也会在一次请求中只过滤一次, 实际上，OncePerRequestFilter是为了兼容不同的web容器，
 * 也就是说其实不是所有的容器都过滤一次。Servlet版本不同，执行的过程也不同。例如：在Servlet2.3中，Filter会过滤一切请求，
 * 包括服务器内部使用forward和<%@ include file=/login.jsp%>的情况，但是在servlet2.4中，Filter默认只会过滤外部请求
 *
 * OncePerRequestFilter extends GenericFilterBean
 * 可以做一些自定义的验证操作
 */
@Component
public class CustomRequestFilter extends OncePerRequestFilter {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {

		// 获取请求头
		final String requestTokenHeader = request.getHeader("Authorization");
		logger.info("OncePerRequestFilter execute");
		chain.doFilter(request, response);
	}

}