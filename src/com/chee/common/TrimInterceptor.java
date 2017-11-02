package com.chee.common;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import java.util.Map;

public class TrimInterceptor extends AbstractInterceptor {
    private static final long serialVersionUID = 4580911991269541929L;

    public String intercept(ActionInvocation invocation) throws Exception {
        Map<String, Object> parameters = invocation.getInvocationContext().getParameters();
        for (String key : parameters.keySet()) {
            Object value = parameters.get(key);
            if ((value instanceof String[])) {
                String[] valueArray = (String[]) value;
                for (int i = 0; i < valueArray.length; i++) {
                    valueArray[i] = valueArray[i].trim();
                }
                parameters.put(key, valueArray);
            }
        }
        return invocation.invoke();
    }
}
