package com.cmos.china.mobile.media.core.service;

import java.util.List;
import java.util.Map;

import com.cmos.china.mobile.media.core.util.ExceptionUtil;




/**
 * OsCache服务接口
 */
public interface ICacheService {
	
	/**
	 * 放入缓存
	 * 
	 * @param cacheKey key
	 * @param value
	 * @return
	 */
	void put2Cache(String cacheKey, String value) throws ExceptionUtil ;

	/**
	 * 放入缓存
	 * 
	 * @param cacheKey key
	 * @param outputObject value
	 * @param expiry 过期时间(s)
	 * @return
	 */
	void put2Cache(String cacheKey, String value, int seconds) throws ExceptionUtil ;

	/**
	 * 从缓存中获取信息
	 * 
	 * @return
	 */
	String getFromCache(String cacheKey) throws ExceptionUtil ;
	
	/**
	 * 清除全部缓存信息
	 */
	void removeAll() throws ExceptionUtil ;
	
	/**
	 * 递增
	 * @return
	 * @throws ControlBusiException
	 */
	long incr(String key) throws ExceptionUtil;
	
	/**
	 * 递增
	 * @return
	 * @throws ControlBusiException
	 */
	long decr(String key) throws ExceptionUtil;
	
	/**
	 * 将Map放入缓存
	 * 
	 * @param key
	 *            缓存的key值
	 * @param map
	 *            待放入的值
	 * @return
	 * @throws ControlBusiException
	 */
	void putMap(String key, Map<String, String> map) throws ExceptionUtil;
	
	
	/**
	 * 从缓存中读取map
	 * @param key 缓存的key值
	 * @return 如果存在key值，则返回Map对象，如果不存在，则返回null
	 * @throws ControlBusiException
	 */
	Map<String, String> getMap(String key) throws ExceptionUtil;
	
	/**
	 * 将List放入缓存
	 * @param key
	 * @param list
	 * @throws ControlBusiException
	 */
	public void putList(String key, Map<String, String> map) throws ExceptionUtil;
	
	public void removeList(String key, Map<String, String> map) throws ExceptionUtil;
	
	/**
	 * 从缓存中读取List
	 * @param key
	 * @return
	 * @throws ControlBusiException
	 */
	public List<Map<String,String>> getList(String key) throws ExceptionUtil;
	
	/**
	 * 设置key过期时间
	 * @param key
	 * @param seconds
	 * @return
	 * @throws ControlBusiException
	 */
	public long setKeyExpire(String key, int seconds) throws ExceptionUtil;
	/**
	 * 根据key值删除实体
	 * @param key
	 * @throws ControlBusiException
	 */
	public void delByKey(String key) throws ExceptionUtil;
}
