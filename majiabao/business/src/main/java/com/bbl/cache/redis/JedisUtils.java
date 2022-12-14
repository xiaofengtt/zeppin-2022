/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.bbl.cache.redis;

import com.bbl.util.SerializeUtil;
import com.bbl.util.SpringContextHolder;
import com.bbl.util.StringUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.*;
import redis.clients.jedis.exceptions.JedisException;

import java.util.*;

/**
 * Jedis Cache 工具类
 * 
 */
public class JedisUtils {
	private static org.slf4j.Logger logger = LoggerFactory.getLogger (JedisUtils.class);

	private static JedisPool jedisPool = SpringContextHolder.getBean (JedisPool.class);
//	 SpringContextHolder.getBean("redisTemplate");

//	private JedisCluster JedisCluster; //集群


	/**
	 * 获取缓存
	 *
	 * @param key 键
	 * @return 值
	 */
	public static String get(String key) {
		String value = null;
		Jedis jedis = null;
		try {
			jedis = getResource ();
			if (jedis.exists (key)) {
				value = jedis.get (key);
				value = StringUtils.isNotBlank (value) && !"nil".equalsIgnoreCase (value) ? value : null;
				logger.debug ("get {} = {}", key, value);
			}
		} catch (Exception e) {
			logger.warn ("get {} = {}", key, value, e);
		} finally {
			jedis.close ();
		}
		return value;
	}

	/**
	 * 获取缓存
	 *
	 * @param key 键
	 * @return 值
	 */
	public static Object getObject(String key) {
		Object value = null;
		Jedis jedis = null;
		try {
			jedis = getResource ();
			if (jedis.exists (getBytesKey (key))) {
				value = toObject (jedis.get (getBytesKey (key)));
				logger.debug ("getObject {} = {}", key, value);
			}
		} catch (Exception e) {
			logger.warn ("getObject {} = {}", key, value, e);
		} finally {
			jedis.close ();
		}
		return value;
	}

	/**
	 * 设置缓存
	 *
	 * @param key          键
	 * @param value        值
	 * @param cacheSeconds 超时时间，0为不超时
	 * @return
	 */
	public static String set(String key, String value, int cacheSeconds) {
		String result = null;
		Jedis jedis = null;
		try {
			jedis = getResource ();
			result = jedis.set (key, value);
			if (cacheSeconds != 0) {
				jedis.expire (key, cacheSeconds);
			}
			logger.debug ("set {} = {}", key, value);
		} catch (Exception e) {
			logger.warn ("set {} = {}", key, value, e);
		} finally {
			jedis.close ();
		}
		return result;
	}


	/**
	 * 设置缓存
	 *
	 * @param key   键
	 * @param value 值
	 * @return
	 */
	public static String set(String key, String value) {
		String result = null;
		Jedis jedis = null;
		try {
			jedis = getResource ();
			result = jedis.set (key, value);
			logger.debug ("set {} = {}", key, value);
		} catch (Exception e) {
			logger.warn ("set {} = {}", key, value, e);
		} finally {
			jedis.close ();
		}
		return result;
	}

	/**
	 * 设置缓存
	 *
	 * @param key          键
	 * @param value        值
	 * @param cacheSeconds 超时时间，0为不超时
	 * @return
	 */
	public static String setObject(String key, Object value, int cacheSeconds) {
		String result = null;
		Jedis jedis = null;
		try {
			jedis = getResource ();
			result = jedis.set (getBytesKey (key), toBytes (value));
			if (cacheSeconds != 0) {
				jedis.expire (key, cacheSeconds);
			}
			logger.debug ("setObject {} = {}", key, value);
		} catch (Exception e) {
			logger.warn ("setObject {} = {}", key, value, e);
		} finally {
			jedis.close ();
		}
		return result;
	}

	/**
	 * 获取List缓存
	 *
	 * @param key 键
	 * @return 值
	 */
	public static List<String> getList(String key) {
		List<String> value = null;
		Jedis jedis = null;
		try {
			jedis = getResource ();
			if (jedis.exists (key)) {
				value = jedis.lrange (key, 0, -1);
				logger.debug ("getList {} = {}", key, value);
			}
		} catch (Exception e) {
			logger.warn ("getList {} = {}", key, value, e);
		} finally {
			jedis.close ();
		}
		return value;
	}

	/**
	 * 获取List缓存
	 *
	 * @param key 键
	 * @return 值
	 */
	public static List<Object> getObjectList(String key) {
		List<Object> value = null;
		Jedis jedis = null;
		try {
			jedis = getResource ();
			if (jedis.exists (getBytesKey (key))) {
				List<byte[]> list = jedis.lrange (getBytesKey (key), 0, -1);
				value = Lists.newArrayList ();
				for (byte[] bs : list) {
					value.add (toObject (bs));
				}
				logger.debug ("getObjectList {} = {}", key, value);
			}
		} catch (Exception e) {
			logger.warn ("getObjectList {} = {}", key, value, e);
		} finally {
			jedis.close ();
		}
		return value;
	}

	/**
	 * 设置List缓存
	 *
	 * @param key          键
	 * @param value        值
	 * @param cacheSeconds 超时时间，0为不超时
	 * @return
	 */
	public static long setList(String key, List<String> value, int cacheSeconds) {
		long result = 0;
		Jedis jedis = null;
		try {
			jedis = getResource ();
			if (jedis.exists (key)) {
				jedis.del (key);
			}
			result = jedis.rpush (key, (String[]) value.toArray ());
			if (cacheSeconds != 0) {
				jedis.expire (key, cacheSeconds);
			}
			logger.debug ("setList {} = {}", key, value);
		} catch (Exception e) {
			logger.warn ("setList {} = {}", key, value, e);
		} finally {
			jedis.close ();
		}
		return result;
	}

	/**
	 * 设置List缓存
	 *
	 * @param key          键
	 * @param value        值
	 * @param cacheSeconds 超时时间，0为不超时
	 * @return
	 */
	public static long setObjectList(String key, List<Object> value, int cacheSeconds) {
		long result = 0;
		Jedis jedis = null;
		try {
			jedis = getResource ();
			if (jedis.exists (getBytesKey (key))) {
				jedis.del (key);
			}
			List<byte[]> list = Lists.newArrayList ();
			for (Object o : value) {
				list.add (toBytes (o));
			}
			result = jedis.rpush (getBytesKey (key), (byte[][]) list.toArray ());
			if (cacheSeconds != 0) {
				jedis.expire (key, cacheSeconds);
			}
			logger.debug ("setObjectList {} = {}", key, value);
		} catch (Exception e) {
			logger.warn ("setObjectList {} = {}", key, value, e);
		} finally {
			jedis.close ();
		}
		return result;
	}

	/**
	 * 向List缓存中添加值
	 *
	 * @param key   键
	 * @param value 值
	 * @return
	 */
	public static long listAdd(String key, String... value) {
		long result = 0;
		Jedis jedis = null;
		try {
			jedis = getResource ();
			result = jedis.rpush (key, value);
			logger.debug ("listAdd {} = {}", key, value);
		} catch (Exception e) {
			logger.warn ("listAdd {} = {}", key, value, e);
		} finally {
			jedis.close ();
		}
		return result;
	}

	/**
	 * 向List缓存中添加值
	 *
	 * @param key   键
	 * @param value 值
	 * @return
	 */
	public static long listObjectAdd(String key, Object... value) {
		long result = 0;
		Jedis jedis = null;
		try {
			jedis = getResource ();
			List<byte[]> list = Lists.newArrayList ();
			for (Object o : value) {
				list.add (toBytes (o));
			}
			result = jedis.rpush (getBytesKey (key), (byte[][]) list.toArray ());
			logger.debug ("listObjectAdd {} = {}", key, value);
		} catch (Exception e) {
			logger.warn ("listObjectAdd {} = {}", key, value, e);
		} finally {
			jedis.close ();
		}
		return result;
	}

	/**
	 * 获取缓存
	 *
	 * @param key 键
	 * @return 值
	 */
	public static Set<String> getSet(String key) {
		Set<String> value = null;
		Jedis jedis = null;
		try {
			jedis = getResource ();
			if (jedis.exists (key)) {
				value = jedis.smembers (key);
				logger.debug ("getSet {} = {}", key, value);
			}
		} catch (Exception e) {
			logger.warn ("getSet {} = {}", key, value, e);
		} finally {
			jedis.close ();
		}
		return value;
	}

	/**
	 * 获取缓存
	 *
	 * @param key 键
	 * @return 值
	 */
	public static Set<Object> getObjectSet(String key) {
		Set<Object> value = null;
		Jedis jedis = null;
		try {
			jedis = getResource ();
			if (jedis.exists (getBytesKey (key))) {
				value = Sets.newHashSet ();
				Set<byte[]> set = jedis.smembers (getBytesKey (key));
				for (byte[] bs : set) {
					value.add (toObject (bs));
				}
				logger.debug ("getObjectSet {} = {}", key, value);
			}
		} catch (Exception e) {
			logger.warn ("getObjectSet {} = {}", key, value, e);
		} finally {
			jedis.close ();
		}
		return value;
	}

	/**
	 * 设置Set缓存
	 *
	 * @param key          键
	 * @param value        值
	 * @param cacheSeconds 超时时间，0为不超时
	 * @return
	 */
	public static long setSet(String key, Set<String> value, int cacheSeconds) {
		long result = 0;
		Jedis jedis = null;
		try {
			jedis = getResource ();
			if (jedis.exists (key)) {
				jedis.del (key);
			}
			result = jedis.sadd (key, (String[]) value.toArray ());
			if (cacheSeconds != 0) {
				jedis.expire (key, cacheSeconds);
			}
			logger.debug ("setSet {} = {}", key, value);
		} catch (Exception e) {
			logger.warn ("setSet {} = {}", key, value, e);
		} finally {
			jedis.close ();
		}
		return result;
	}

	/**
	 * 设置Set缓存
	 *
	 * @param key          键
	 * @param value        值
	 * @param cacheSeconds 超时时间，0为不超时
	 * @return
	 */
	public static long setObjectSet(String key, Set<Object> value, int cacheSeconds) {
		long result = 0;
		Jedis jedis = null;
		try {
			jedis = getResource ();
			if (jedis.exists (getBytesKey (key))) {
				jedis.del (key);
			}
			Set<byte[]> set = Sets.newHashSet ();
			for (Object o : value) {
				set.add (toBytes (o));
			}
			result = jedis.sadd (getBytesKey (key), (byte[][]) set.toArray ());
			if (cacheSeconds != 0) {
				jedis.expire (key, cacheSeconds);
			}
			logger.debug ("setObjectSet {} = {}", key, value);
		} catch (Exception e) {
			logger.warn ("setObjectSet {} = {}", key, value, e);
		} finally {
			jedis.close ();
		}
		return result;
	}

	/**
	 * 向Set缓存中添加值
	 *
	 * @param key   键
	 * @param value 值
	 * @return
	 */
	public static long setSetAdd(String key, String... value) {
		long result = 0;
		Jedis jedis = null;
		try {
			jedis = getResource ();
			result = jedis.sadd (key, value);
			logger.debug ("setSetAdd {} = {}", key, value);
		} catch (Exception e) {
			logger.warn ("setSetAdd {} = {}", key, value, e);
		} finally {
			jedis.close ();
		}
		return result;
	}

	/**
	 * 向Set缓存中添加值
	 *
	 * @param key   键
	 * @param value 值
	 * @return
	 */
	public static long setSetObjectAdd(String key, Object... value) {
		long result = 0;
		Jedis jedis = null;
		try {
			jedis = getResource ();
			Set<byte[]> set = Sets.newHashSet ();
			for (Object o : value) {
				set.add (toBytes (o));
			}
			result = jedis.rpush (getBytesKey (key), (byte[][]) set.toArray ());
			logger.debug ("setSetObjectAdd {} = {}", key, value);
		} catch (Exception e) {
			logger.warn ("setSetObjectAdd {} = {}", key, value, e);
		} finally {
			jedis.close ();
		}
		return result;
	}

	/**
	 * 获取Map缓存
	 *
	 * @param key 键
	 * @return 值
	 */
	public static Map<String, String> getMap(String key) {
		Map<String, String> value = null;
		Jedis jedis = null;
		try {
			jedis = getResource ();
			if (jedis.exists (key)) {
				value = jedis.hgetAll (key);
				logger.debug ("getMap {} = {}", key, value);
			}
		} catch (Exception e) {
			logger.warn ("getMap {} = {}", key, value, e);
		} finally {
			jedis.close ();
		}
		return value;
	}

	/**
	 * 获取Map缓存
	 *
	 * @param key 键
	 * @return 值
	 */
	public static Map<String, Object> getObjectMap(String key) {
		Map<String, Object> value = null;
		Jedis jedis = null;
		try {
			jedis = getResource ();
			if (jedis.exists (getBytesKey (key))) {
				value = Maps.newHashMap ();
				Map<byte[], byte[]> map = jedis.hgetAll (getBytesKey (key));
				for (Map.Entry<byte[], byte[]> e : map.entrySet ()) {
					value.put (StringUtils.toString (e.getKey ()), toObject (e.getValue ()));
				}
				logger.debug ("getObjectMap {} = {}", key, value);
			}
		} catch (Exception e) {
			logger.warn ("getObjectMap {} = {}", key, value, e);
		} finally {
			jedis.close ();
		}
		return value;
	}

	/**
	 * 设置Map缓存
	 *
	 * @param key          键
	 * @param value        值
	 * @param cacheSeconds 超时时间，0为不超时
	 * @return
	 */
	public static String setMap(String key, Map<String, String> value, int cacheSeconds) {
		String result = null;
		Jedis jedis = null;
		try {
			jedis = getResource ();
			if (jedis.exists (key)) {
				jedis.del (key);
			}
			result = jedis.hmset (key, value);
			if (cacheSeconds != 0) {
				jedis.expire (key, cacheSeconds);
			}
			logger.debug ("setMap {} = {}", key, value);
		} catch (Exception e) {
			logger.warn ("setMap {} = {}", key, value, e);
		} finally {
			jedis.close ();
		}
		return result;
	}

	/**
	 * 设置Map缓存
	 *
	 * @param key          键
	 * @param value        值
	 * @param cacheSeconds 超时时间，0为不超时
	 * @return
	 */
	public static String setObjectMap(String key, Map<String, Object> value, int cacheSeconds) {
		String result = null;
		Jedis jedis = null;
		try {
			jedis = getResource ();
			if (jedis.exists (getBytesKey (key))) {
				jedis.del (key);
			}
			Map<byte[], byte[]> map = Maps.newHashMap ();
			for (Map.Entry<String, Object> e : value.entrySet ()) {
				map.put (getBytesKey (e.getKey ()), toBytes (e.getValue ()));
			}
			result = jedis.hmset (getBytesKey (key), (Map<byte[], byte[]>) map);
			if (cacheSeconds != 0) {
				jedis.expire (key, cacheSeconds);
			}
			logger.debug ("setObjectMap {} = {}", key, value);
		} catch (Exception e) {
			logger.warn ("setObjectMap {} = {}", key, value, e);
		} finally {
			jedis.close ();
		}
		return result;
	}

	/**
	 * 向Map缓存中添加值
	 *
	 * @param key   键
	 * @param value 值
	 * @return
	 */
	public static String mapPut(String key, Map<String, String> value) {
		String result = null;
		Jedis jedis = null;
		try {
			jedis = getResource ();
			result = jedis.hmset (key, value);
			logger.debug ("mapPut {} = {}", key, value);
		} catch (Exception e) {
			logger.warn ("mapPut {} = {}", key, value, e);
		} finally {
			jedis.close ();
		}
		return result;
	}

	/**
	 * 向Map缓存中添加值
	 *
	 * @param key   键
	 * @param value 值
	 * @return
	 */
	public static String mapObjectPut(String key, Map<String, Object> value) {
		String result = null;
		Jedis jedis = null;
		try {
			jedis = getResource ();
			Map<byte[], byte[]> map = Maps.newHashMap ();
			for (Map.Entry<String, Object> e : value.entrySet ()) {
				map.put (getBytesKey (e.getKey ()), toBytes (e.getValue ()));
			}
			result = jedis.hmset (getBytesKey (key), (Map<byte[], byte[]>) map);
			logger.debug ("mapObjectPut {} = {}", key, value);
		} catch (Exception e) {
			logger.warn ("mapObjectPut {} = {}", key, value, e);
		} finally {
			jedis.close ();
		}
		return result;
	}

	/**
	 * 移除Map缓存中的值
	 *
	 * @param key 键
	 * @return
	 */
	public static long mapRemove(String key, String mapKey) {
		long result = 0;
		Jedis jedis = null;
		try {
			jedis = getResource ();
			result = jedis.hdel (key, mapKey);
			logger.debug ("mapRemove {}  {}", key, mapKey);
		} catch (Exception e) {
			logger.warn ("mapRemove {}  {}", key, mapKey, e);
		} finally {
			jedis.close ();
		}
		return result;
	}

	/**
	 * 移除Map缓存中的值
	 *
	 * @param key 键
	 * @return
	 */
	public static long mapObjectRemove(String key, String mapKey) {
		long result = 0;
		Jedis jedis = null;
		try {
			jedis = getResource ();
			result = jedis.hdel (getBytesKey (key), getBytesKey (mapKey));
			logger.debug ("mapObjectRemove {}  {}", key, mapKey);
		} catch (Exception e) {
			logger.warn ("mapObjectRemove {}  {}", key, mapKey, e);
		} finally {
			jedis.close ();
		}
		return result;
	}

	/**
	 * 判断Map缓存中的Key是否存在
	 *
	 * @param key 键
	 * @return
	 */
	public static boolean mapExists(String key, String mapKey) {
		boolean result = false;
		Jedis jedis = null;
		try {
			jedis = getResource ();
			result = jedis.hexists (key, mapKey);
			logger.debug ("mapExists {}  {}", key, mapKey);
		} catch (Exception e) {
			logger.warn ("mapExists {}  {}", key, mapKey, e);
		} finally {
			jedis.close ();
		}
		return result;
	}

	/**
	 * 判断Map缓存中的Key是否存在
	 *
	 * @param key 键
	 * @return
	 */
	public static boolean mapObjectExists(String key, String mapKey) {
		boolean result = false;
		Jedis jedis = null;
		try {
			jedis = getResource ();
			result = jedis.hexists (getBytesKey (key), getBytesKey (mapKey));
			logger.debug ("mapObjectExists {}  {}", key, mapKey);
		} catch (Exception e) {
			logger.warn ("mapObjectExists {}  {}", key, mapKey, e);
		} finally {
			jedis.close ();
		}
		return result;
	}

	/**
	 * 删除缓存
	 *
	 * @param key 键
	 * @return
	 */
	public static long del(String key) {
		long result = 0;
		Jedis jedis = null;
		try {
			jedis = getResource ();
			if (jedis.exists (key)) {
				result = jedis.del (key);
				logger.debug ("del {}", key);
			} else {
				logger.debug ("del {} not exists", key);
			}
		} catch (Exception e) {
			logger.warn ("del {}", key, e);
		} finally {
			jedis.close ();
		}
		return result;
	}

	/**
	 * 模糊删除缓存
	 *
	 * @param prex 键
	 * @return
	 */
	public static long patternDel(String prex) {
		long result = 0;
		Jedis jedis = null;
		try {
			jedis = getResource ();
			Set<String> keys = jedis.keys (prex + "*");
			logger.debug (keys.size () + "");
			for (String key : keys) {
				if (jedis.exists (key)) {
					result = jedis.del (key);
					logger.debug ("del {}", key);
				} else {
					logger.debug ("del {} not exists", key);
				}
			}
		} catch (Exception e) {
			logger.warn ("del {}", prex, e);
		} finally {
			jedis.close ();
		}
		return result;
	}

	/**
	 * 删除缓存
	 *
	 * @param key 键
	 * @return
	 */
	public static long delObject(String key) {
		long result = 0;
		Jedis jedis = null;
		try {
			jedis = getResource ();
			if (jedis.exists (getBytesKey (key))) {
				result = jedis.del (getBytesKey (key));
				logger.debug ("delObject {}", key);
			} else {
				logger.debug ("delObject {} not exists", key);
			}
		} catch (Exception e) {
			logger.warn ("delObject {}", key, e);
		} finally {
			jedis.close ();
		}
		return result;
	}

	/**
	 * 缓存是否存在
	 *
	 * @param key 键
	 * @return
	 */
	public static boolean exists(String key) {
		boolean result = false;
		Jedis jedis = null;
		try {
			jedis = getResource ();
			result = jedis.exists (key);
			logger.debug ("exists {}", key);
		} catch (Exception e) {
			logger.warn ("exists {}", key, e);
		} finally {
			jedis.close ();
		}
		return result;
	}

	/**
	 * 缓存是否存在
	 *
	 * @param key 键
	 * @return
	 */
	public static boolean existsObject(String key) {
		boolean result = false;
		Jedis jedis = null;
		try {
			jedis = getResource ();
			result = jedis.exists (getBytesKey (key));
			logger.debug ("existsObject {}", key);
		} catch (Exception e) {
			logger.warn ("existsObject {}", key, e);
		} finally {

			jedis.close ();
		}
		return result;
	}

	/**
	 * 获取资源
	 *
	 * @return
	 * @throws JedisException
	 */
	public static Jedis getResource() throws JedisException {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource ();
//			logger.debug("getResource.", jedis);
		} catch (JedisException e) {
			logger.warn ("getResource.", e);
			jedis.close ();

			throw e;
		}
		return jedis;
	}


	/**
	 * redis 发布
	 *
	 * @return
	 */
	public static void subscribeMsg(JedisPubSub jedisPubSub, String channels) {

		String value = null;
		Jedis jedis = null;
		try {
			jedis = getResource ();
			jedis.subscribe (jedisPubSub, channels);
		} catch (Exception e) {
			logger.warn ("subscribeMsg {} = {}", channels, value, e);
		} finally {
			jedis.close ();
		}
	}


	/**
	 * redis 订阅
	 *
	 * @return
	 */
	public static void publishMsg(String channels, String value) {

		Jedis jedis = null;
		try {
			jedis = getResource ();
			jedis.publish (channels, value);
		} catch (Exception e) {
			logger.warn ("publishMsg {} = {}", channels, value, e);
		} finally {
			jedis.close ();
		}
	}


	/**
	 * redis LPUSH
	 *
	 * @return
	 */

	public static long  LPUSH(String key, String content) {

		Jedis jedis = null;
		try {
			jedis = getResource ();
			return jedis.lpush (key, content);


		} catch (Exception e) {
			logger.warn ("setStream {} = {}", key, content, e);
		} finally {
			jedis.close ();
		}

		return -1;
	}

	/**
	 * redis LPUSH
	 *
	 * @return
	 */

	public static List<String>  BRPOP (String key) {

		Jedis jedis = null;
		try {
			jedis = getResource ();
			List<String> temp=jedis.blpop (0,key);
			return  temp;


		} catch (Exception e) {
			logger.warn ("setStream {} = {}", key, key, e);
		} finally {
			jedis.close ();
		}


		return null;
	}


	/**
	 * redis Stream
	 *
	 * @return
	 */

	public static StreamEntryID  setStream(String key, Map content) {

		Jedis jedis = null;
		try {
			jedis = getResource ();
			//new StreamEntryID().NEW_ENTR = * ，代表id由redis生成
			//也可以指定stream最多存储多少条记录。--自己查api
			return jedis.xadd(key, new StreamEntryID().NEW_ENTRY, content);


		} catch (Exception e) {
			logger.warn ("setStream {} = {}", key, content, e);
		} finally {
			jedis.close ();
		}

		return null;
	}


	/**
	 * redis Stream
	 * @return
	 */
	public   static  List<Map.Entry<String, List<StreamEntry>>> getStream  (int count, int block, Map.Entry<String, StreamEntryID>... streams) {

		Jedis jedis = null;
		try {
			jedis = getResource ();
			return jedis.xread(count, block, streams);

		} catch (Exception e) {
			logger.warn("xadd {} = {}", count, block, e);
		} finally {
			jedis.close();
		}
		return null;
	}


	/**
	 * 获取byte[]类型Key
	 * @return
	 */
	public static byte[] getBytesKey(Object object){
		if(object instanceof String){
    		return StringUtils.getBytes((String)object);
    	}else{
    		return SerializeUtil.serialize(object);
    	}
	}

	/**
	 * Object转换byte[]类型
	 * @return
	 */
	public static byte[] toBytes(Object object){
    	return SerializeUtil.serialize(object);
	}

	/**
	 * byte[]型转换Object
	 * @return
	 */
	public static Object toObject(byte[] bytes){
		return SerializeUtil.unserialize(bytes);
	}




}
