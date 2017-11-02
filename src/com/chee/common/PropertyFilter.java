package com.chee.common;

import com.chee.util.ConvertUtils;
import com.chee.util.ServletUtils;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.Assert;

public class PropertyFilter {
    public static final String OR_SEPARATOR = "_OR_";
    private MatchType matchType = null;
    private Object matchValue = null;
    private Class<?> propertyClass = null;
    private String[] propertyNames = null;

    public PropertyFilter() {
    }

    public PropertyFilter(String filterName, Object value) {
        String firstPart = StringUtils.substringBefore(filterName, "_");
        String matchTypeCode = StringUtils.substring(firstPart, 0, firstPart.length() - 1);
        String propertyTypeCode = StringUtils.substring(firstPart, firstPart.length() - 1, firstPart.length());
        try {
            this.matchType = ((MatchType) Enum.valueOf(MatchType.class, matchTypeCode));
        } catch (RuntimeException e) {
            throw new IllegalArgumentException("filter名称" + filterName + "没有按规则编写,无法得到属性比较类型.", e);
        }
        try {
            this.propertyClass = ((PropertyType) Enum.valueOf(PropertyType.class, propertyTypeCode)).getValue();
        } catch (RuntimeException e) {
            throw new IllegalArgumentException("filter名称" + filterName + "没有按规则编写,无法得到属性值类型.", e);
        }
        String propertyNameStr = StringUtils.substringAfter(filterName, "_");
        Assert.isTrue(StringUtils.isNotBlank(propertyNameStr), "filter名称" + filterName + "没有按规则编写,无法得到属性名称.");
        this.propertyNames = StringUtils.splitByWholeSeparator(propertyNameStr, "_OR_");
        if ((value instanceof Enum)) {
            this.matchValue = value;
        } else {
            this.matchValue = ConvertUtils.convertStringToObject(value, this.propertyClass);
        }
    }

    public static List<PropertyFilter> buildFromHttpRequest(HttpServletRequest request) {
        return buildFromHttpRequest(request, "filter");
    }

    public static List<PropertyFilter> buildFromHttpRequest(HttpServletRequest request, String filterPrefix) {
        List<PropertyFilter> filterList = new ArrayList();

        Map<String, Object> filterParamMap = ServletUtils.getParametersStartingWith(request, filterPrefix + "_");
        for (Map.Entry<String, Object> entry : filterParamMap.entrySet()) {
            String filterName = (String) entry.getKey();
            String value = (String) entry.getValue();
            if (StringUtils.isNotBlank(value)) {
                PropertyFilter filter = new PropertyFilter(filterName, value);
                filterList.add(filter);
            }
        }
        return filterList;
    }

    public Class<?> getPropertyClass() {
        return this.propertyClass;
    }

    public MatchType getMatchType() {
        return this.matchType;
    }

    public Object getMatchValue() {
        return this.matchValue;
    }

    public String[] getPropertyNames() {
        return this.propertyNames;
    }

    public String getPropertyName() {
        Assert.isTrue(this.propertyNames.length == 1, "There are not only one property in this filter.");
        return this.propertyNames[0];
    }

    public boolean hasMultiProperties() {
        return this.propertyNames.length > 1;
    }

    public static enum PropertyType {
        S(String.class), I(Integer.class), L(Long.class), N(Double.class), D(Date.class), B(Boolean.class), E(Enum.class);

        private Class<?> clazz;

        private PropertyType(Class<?> clazz) {
            this.clazz = clazz;
        }

        public Class<?> getValue() {
            return this.clazz;
        }
    }

    public static enum MatchType {
        EQ, LIKE, LT, GT, LE, GE, ISNULL, ISNOTNULL;
    }
}
