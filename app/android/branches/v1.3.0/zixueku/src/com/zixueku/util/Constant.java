package com.zixueku.util;

import java.util.Hashtable;

/**
 * 定义常量
 * 
 * @author Administrator
 * 
 */
public class Constant {

	public final static int SUCCESS = 1;
	public final static int NET_FAILED = 2;
	// 缓存图片路径
	public static final String BASE_IMAGE_CACHE = "cache/images/";
	
	public static final int LOADING_ITEM_TIME=200;
	public static final int LOADING_TIME=0;
	
	public static final int ItemClickChangeToNextViewPagerTime=0;

	/**
	 * Unknown network class
	 */
	public static final int NETWORK_CLASS_UNKNOWN = 0;

	/**
	 * wifi net work
	 */
	public static final int NETWORK_WIFI = 1;

	/**
	 * "2G" networks
	 */
	public static final int NETWORK_CLASS_2_G = 2;

	/**
	 * "3G" networks
	 */
	public static final int NETWORK_CLASS_3_G = 3;

	/**
	 * "4G" networks
	 */
	public static final int NETWORK_CLASS_4_G = 4;

	public static final Hashtable<String, Short> AUTH_PLATFORMS = new Hashtable<String, Short>() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * 
		 */

		{
			this.put("QQ", (short) 1);
			this.put("Wechat", (short) 2);
			this.put("SinaWeibo", (short) 3);
			this.put("Renren", (short) 4);
		};
	};

	public static final Hashtable<String, String> OPTION_INDEX = new Hashtable<String, String>() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * 
		 */
		{
			this.put("1", "A");
			this.put("2", "B");
			this.put("3", "C");
			this.put("4", "D");
			this.put("5", "E");
			this.put("6", "F");
			this.put("7", "G");
		};
	};

	// gender:1|2 (1：男，2女）
	public static final Hashtable<String, Short> USERGENDER = new Hashtable<String, Short>() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		{
			this.put("m", (short) 1);
			this.put("f", (short) 2);
		}
	};

	public static class Config {
		public static final boolean DEVELOPER_MODE = false;
	}

}