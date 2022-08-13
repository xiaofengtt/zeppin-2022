package com.cmos.china.mobile.media.core.util;

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
	 * Get a value based on key , if key does not exist , null is returned
	 * 
	 * @param key
	 * @return
	 */
	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			logger.error("PropertiesUtilError",e);
			return null;
		}
	}

}