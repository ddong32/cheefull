package com.chee.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.lang.StringUtils;

public class ConvertUtils {
    static {
    }

    public static List convertElementPropertyToList(Collection collection, String propertyName) {
        List list = new ArrayList();
        try {
            for (Object obj : collection) {
                list.add(PropertyUtils.getProperty(obj, propertyName));
            }
        } catch (Exception e) {
            throw ReflectionUtils.convertReflectionExceptionToUnchecked(e);
        }
        return list;
    }

    public static String convertElementPropertyToString(Collection collection, String propertyName, String separator) {
        List list = convertElementPropertyToList(collection, propertyName);
        return StringUtils.join(list, separator);
    }

    public static Object convertStringToObject(Object value, Class<?> toType) {
        try {
            return org.apache.commons.beanutils.ConvertUtils.convert(value, toType);
        } catch (Exception e) {
            throw ReflectionUtils.convertReflectionExceptionToUnchecked(e);
        }
    }

    private static void registerDateConverter() {
        DateConverter dc = new DateConverter();
        dc.setUseLocaleFormat(true);
        dc.setPatterns(new String[] { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss" });
        org.apache.commons.beanutils.ConvertUtils.register(dc, Date.class);
    }
}
