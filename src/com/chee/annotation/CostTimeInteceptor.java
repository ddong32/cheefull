package com.chee.annotation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class CostTimeInteceptor extends HandlerInterceptorAdapter {
    private static final Logger log = LoggerFactory.getLogger(CostTimeInteceptor.class);

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        long startTime = System.currentTimeMillis();
        request.setAttribute("startTime", Long.valueOf(startTime));
        return true;
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        long startTime = ((Long) request.getAttribute("startTime")).longValue();
        long endTime = System.currentTimeMillis();
        long executeTime = endTime - startTime;
        if (log.isInfoEnabled()) {
            log.info("[" + request.getRequestURI() + "] executeTime : " + executeTime + "ms");
        }
    }
}
