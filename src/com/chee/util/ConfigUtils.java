package com.chee.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import org.apache.log4j.Logger;

public class ConfigUtils {
	public static Logger log = Logger.getLogger(ConfigUtils.class);
	public static Properties prop = null;

	public static void getProperInstance() {
		if (prop == null) {
			prop = new Properties();
		}
	}

	public static void savaOrUpdateProper(String key, String value) {
		InputStream is = null;
		OutputStream os = null;
		try {
			is = Thread.currentThread().getContextClassLoader()
					.getResourceAsStream("config.properties");
			if (prop == null) {
				getProperInstance();
			}
			prop.load(is);
			is.close();
			prop.setProperty(key, value);
			os = new FileOutputStream("src" + File.separator
					+ "config.properties");
			prop.store(os, null);
		} catch (Exception e) {
			log.error("It has error when sava Or update properties");
			try {
				if (is != null) {
					is.close();
				}
				if (os != null) {
					os.close();
				}
			} catch (Exception xe) {
				log.error("It has error when close InputStream and OutputStream");
			}
		} finally {
			try {
				if (is != null) {
					is.close();
				}
				if (os != null) {
					os.close();
				}
			} catch (Exception e) {
				log.error("It has error when close InputStream and OutputStream");
			}
		}
	}
}
