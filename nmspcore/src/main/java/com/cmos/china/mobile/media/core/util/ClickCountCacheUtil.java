package com.cmos.china.mobile.media.core.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.cmos.core.logger.Logger;
import com.cmos.core.logger.LoggerFactory;
import com.cmos.core.util.StringUtil;
import com.cmos.nosql.redis.util.JedisUtil;




/**
 * 视频点击量缓存存取工具
 * @author zeppin
 *	
 */
public class ClickCountCacheUtil {
	
	
	private static Logger logger = LoggerFactory.getUtilLog(ClickCountCacheUtil.class);
	
	/**
	 * 当天点击量增加
	 * @param id 视频id
	 * @throws ExceptionUtil 
	 * key 格式 clickCount2017-03-16id
	 */
	public static void setClickCountCacheById(String id) throws ExceptionUtil{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String day = format.format(calendar.getTime());
		String keyClickCount = "clickCount"+day+id;
		try {
			JedisUtil.getCluster().incr(keyClickCount);
		} catch (Exception e) {
			logger.error(Constants.error_access_redis+"增加点击量失败 setClickCountCacheById", e);
			throw new ExceptionUtil("增加点击量失败");
		}	
	}
	
	/**
	 * 获取全部点击量 需定时任务取前一天的加之前天数
	 * @param id
	 * @return count
	 *  key 格式 clickCountid
	 * @throws ExceptionUtil 
	 */
	public static int getClickCountCacheById(String id) throws ExceptionUtil{
		String keyClickCount = "clickCount"+id;
		try {
			String result = JedisUtil.getCluster().get(keyClickCount);
			if(StringUtil.isNotEmpty(result)){
				return Integer.parseInt(result);
			}else{
				return 0;
			}
		} catch (Exception e) {
			logger.error(Constants.error_access_redis+"获取点击量失败 getClickCountCacheById", e);
			throw new ExceptionUtil("获取点击量失败");
		}
	}
	
	/**
	 * 获取区间点击量 
	 * @param id 视频id
	 * @param days  天数（当前往前数）
	 * @return count  
	 * key 格式 clickCount2017-03-16id
	 * @throws ExceptionUtil 
	 */
	public static int getClickCountCacheById(String id,int days) throws ExceptionUtil{
		int ClickCount = 0;
		for(int i = 0 ; i < days ; i++){
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			calendar.add(Calendar.DAY_OF_MONTH, -(i+1));
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String day = format.format(calendar.getTime());
			String keyClickCount = "clickCount"+day+id;
			try {
				String result = JedisUtil.getCluster().get(keyClickCount);
				if(StringUtil.isNotEmpty(result)){
					ClickCount += Integer.parseInt(result);
				}
				logger.info(keyClickCount+"#"+result);
			} catch (Exception e) {
				logger.error(Constants.error_access_redis+"获取点击量失败 getClickCountCacheById", e);
				throw new ExceptionUtil("获取点击量失败");
			}	
		}
		return ClickCount;
	}
	
	
	/**
	 * 获取当天点击量 
	 * @param id 视频id
	 * @return count  
	 * key 格式 clickCount2017-3-16id
	 * @throws ExceptionUtil 
	 */
	public static int getClickCountById(String id) throws ExceptionUtil{
		int ClickCount = 0;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String day = format.format(calendar.getTime());
		String keyClickCount = "clickCount"+day+id;
		try {
				String result = JedisUtil.getCluster().get(keyClickCount);
				if(StringUtil.isNotEmpty(result)){
					ClickCount += Integer.parseInt(result);
				}
				logger.info(keyClickCount+"#"+result);
			} catch (Exception e) {
				logger.error(Constants.error_access_redis+"获取当天点击量失败 getClickCountCacheById", e);
				throw new ExceptionUtil("获取当天点击量失败");
			}	
		return ClickCount;
	}
}
