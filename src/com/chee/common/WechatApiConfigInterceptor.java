package com.chee.common;

import com.chee.util.WechatApiUtil;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.weixin.sdk.api.ApiConfig;
import com.jfinal.weixin.sdk.api.ApiConfigKit;

public class WechatApiConfigInterceptor implements Interceptor {
    public void intercept(Invocation inv) {
        try {
            ApiConfig ac = WechatApiUtil.getApiConfig();
            ApiConfigKit.setThreadLocalApiConfig(ac);
            inv.invoke();
        } finally {
            ApiConfigKit.removeThreadLocalApiConfig();
        }
    }
}
