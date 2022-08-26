package com.xmxe.config.handler;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义登录失败逻辑处理器 implements AuthenticationFailureHandler
 * 登陆失败后的处理类
 */
@Component
public class CustomFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        // 自定义session里面的内容信息
        request.getSession().setAttribute("error",exception.getMessage());
        this.redirectStrategy.sendRedirect(request, response, "login");
        // super.onAuthenticationFailure(request, response, exception);

    }
}