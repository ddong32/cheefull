package com.chee.common;

import com.jfinal.core.JFinalFilter;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public final class NewJFinalFilter implements Filter {
    JFinalFilter jFinalFilter = new JFinalFilter();

    public void init(FilterConfig filterConfig) throws ServletException {
        this.jFinalFilter.init(filterConfig);
    }

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;

        String uri = request.getRequestURI();
        if (uri.contains("Wechat!menuSync.action")) {
            this.jFinalFilter.doFilter(req, res, chain);
        } else {
            chain.doFilter(req, res);
        }
    }

    public void destroy() {
        this.jFinalFilter.destroy();
    }
}
