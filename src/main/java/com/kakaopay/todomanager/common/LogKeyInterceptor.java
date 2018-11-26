package com.kakaopay.todomanager.common;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * Created by jyp on 2018. 11. 27.
 * Email :ssm027@gmail.com
 */

@Slf4j
public class LogKeyInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        MDC.put("logkey", UUID.randomUUID().toString().substring(0, 7));

        return super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        MDC.clear();
        super.postHandle(request, response, handler, modelAndView);
    }
}
