package com.cmos.chinamobile.media.util;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import com.cmos.core.logger.Logger;
import com.cmos.core.logger.LoggerFactory;

/**
 * Parsing The Configuration File
 * 
 */
public final class PropertiesUtil {
	private static Logger logger = LoggerFactory.getUtilLog(PropertiesUtil.class);
	private static final String BUNDLE_NAME = "config/system";
	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
			.getBundle(BUNDLE_NAME);

	private PropertiesUtil() {
	}

	/**
	 * 根据key获取值，key不存在则返回null
	 * 
	 * @param key
	 * @return
	 */
	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			logger.error("PropertiesUtilError", e);
			return null;
		}
	}

	/**
	 * 根据key获取�?
	 * 
	 * @param key
	 * @return
	 */
	public static int getInt(String key) {
		return Integer.parseInt(getString(key));
	}
}