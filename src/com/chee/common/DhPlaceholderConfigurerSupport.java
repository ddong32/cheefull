package com.chee.common;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Properties;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

public class DhPlaceholderConfigurerSupport extends PropertyPlaceholderConfigurer {
    protected String resolvePlaceholder(String placeholder, Properties props) {
        if ((placeholder != null) && (placeholder.endsWith(".REAL"))) {
            return getFilePath() + props.getProperty(placeholder.substring(0, placeholder.indexOf(".REAL")));
        }
        return props.getProperty(placeholder);
    }

    private static String getFilePath() {
        String fileUploadPath = null;
        URL url = Thread.currentThread().getContextClassLoader().getResource("");
        fileUploadPath = url.getPath().substring(0, url.getPath().lastIndexOf("/WEB-INF"));
        int index = fileUploadPath.lastIndexOf('/');
        if (index > 0) {
            index = fileUploadPath.substring(0, index).lastIndexOf('/');
        }
        if (index > 0) {
            fileUploadPath = fileUploadPath.substring(0, index);
        }
        try {
            fileUploadPath = URLDecoder.decode(fileUploadPath, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            fileUploadPath = "";
        }
        return fileUploadPath;
    }
}
