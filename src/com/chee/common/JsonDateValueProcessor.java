package com.chee.common;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

public class JsonDateValueProcessor implements JsonValueProcessor {
    private String datePattern = "yyyy-MM-dd HH:mm:ss";

    public JsonDateValueProcessor() {
    }

    public JsonDateValueProcessor(String format) {
        this.datePattern = format;
    }

    public Object processArrayValue(Object value, JsonConfig jsonConfig) {
        return process(value);
    }

    public Object processObjectValue(String key, Object value, JsonConfig jsonConfig) {
        return process(value);
    }

    private Object process(Object value) {
        try {
            if ((value instanceof Date)) {
                SimpleDateFormat sdf = new SimpleDateFormat(this.datePattern, Locale.UK);
                return sdf.format((Date) value);
            }
            return value == null ? "" : value.toString();
        } catch (Exception localException) {
        }
        return "";
    }

    public String getDatePattern() {
        return this.datePattern;
    }

    public void setDatePattern(String pDatePattern) {
        this.datePattern = pDatePattern;
    }
}
