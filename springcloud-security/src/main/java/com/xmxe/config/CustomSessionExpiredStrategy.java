package com.xmxe.config;

import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义session过期策略
 */
@Component
public class CustomSessionExpiredStrategy implements SessionInformationExpiredStrategy {
    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent sessionInformationExpiredEvent) throws IOException, ServletException {
        HttpServletResponse response = sessionInformationExpiredEvent.getResponse();
        response.sendRedirect("login");
//        response.setContentType("application/json;charset=UTF-8");
//        response.getWriter().print("登陆错误-相同用户只允许登陆一个设备");
//        response.flushBuffer();
    }
}
