package com.chee.common;

import com.chee.service.ResourceService;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.security.ConfigAttributeDefinition;
import org.springframework.security.ConfigAttributeEditor;
import org.springframework.security.intercept.web.DefaultFilterInvocationDefinitionSource;
import org.springframework.security.intercept.web.FilterInvocationDefinitionSource;
import org.springframework.security.intercept.web.RequestKey;
import org.springframework.security.util.AntUrlPathMatcher;
import org.springframework.security.util.UrlMatcher;
import org.springframework.stereotype.Component;

@Component
public class UserSecurityDefinitionSource implements FactoryBean<Object> {
    @javax.annotation.Resource
    private ResourceService resourceService;

    public boolean isSingleton() {
        return true;
    }

    public Class<FilterInvocationDefinitionSource> getObjectType() {
        return FilterInvocationDefinitionSource.class;
    }

    protected UrlMatcher getUrlMatcher() {
        return new AntUrlPathMatcher();
    }

    public Object getObject() throws Exception {
        return new DefaultFilterInvocationDefinitionSource(getUrlMatcher(), buildRequestMap());
    }

    protected LinkedHashMap<RequestKey, ConfigAttributeDefinition> buildRequestMap() throws Exception {
        LinkedHashMap<RequestKey, ConfigAttributeDefinition> resultMap = new LinkedHashMap();
        ConfigAttributeEditor configAttributeEditor = new ConfigAttributeEditor();
        Map<String, String> resourceMap = getResourceMap();
        for (Map.Entry<String, String> entry : resourceMap.entrySet()) {
            RequestKey key = new RequestKey((String) entry.getKey(), null);
            configAttributeEditor.setAsText((String) entry.getValue());
            resultMap.put(key, (ConfigAttributeDefinition) configAttributeEditor.getValue());
        }
        return resultMap;
    }

    protected Map<String, String> getResourceMap() {
        Map<String, String> resourceMap = new LinkedHashMap();
        List<com.chee.entity.Resource> resourceList = this.resourceService.find("from Resource where value is not null order by sort asc", new Object[0]);
        for (com.chee.entity.Resource resource : resourceList) {
            String resourceValue = resource.getValue();
            if ((StringUtils.isNotEmpty(resourceValue)) && (StringUtils.isNotEmpty(resource.getRoleValues()))) {
                resourceMap.put(resourceValue + "**", resource.getRoleValues());
            }
        }
        resourceMap.put("/install/**", "ROLE_INSTALL");
        resourceMap.put("/admin/**", "IS_AUTHENTICATED_FULLY");
        return resourceMap;
    }
}
