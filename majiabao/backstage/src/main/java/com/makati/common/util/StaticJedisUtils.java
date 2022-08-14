/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.makati.common.util;

import com.makati.business.blacklist.entity.Blacklist;
import com.makati.common.web.SpringContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import redis.clients.jedis.Jedis;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;


public class StaticJedisUtils {

	private static Logger logger = LoggerFactory.getLogger(StaticJedisUtils.class);

/*  接口1 ValueOperations<K, V>----------------------------字符串
	接口2：ListOperations<K, V>-------------------------------List
	接口3：SetOperations<K, V> ------------------------------Set
	接口4：ZSetOperations<K, V>-----------------------------有序Set
	接口5：HashOperations<K, HK, HV>---------------------Hash
	接口5：GeoOperations<K, HK, HV>---------------------地图*/
	private static RedisTemplate<Object, Object> redisTemplate = SpringContextHolder.getBean("redisTemplate");

	/**
	 * 添加hyperLogLog
	 *
	 * @return 值
	 */
	public static Long addhyperLog(String key,String vaule) {
        Long onlineToday = redisTemplate.opsForHyperLogLog ().add (key, vaule);
        return onlineToday == null ? null : onlineToday;
	}


    /**
     * 查询hyperLogLog
     *
     * @return 值
     */
    public static Long gethyperLog(String key) {
        Long onlineToday = redisTemplate.opsForHyperLogLog ().size (key);
        return onlineToday == null ? null : onlineToday;
    }


	/**
	 * 获取缓存
	 *
	 * @param key 键
	 * @return 值
	 */
	public static String get(String key) {
		Object o = redisTemplate.opsForValue().get(key);
		return o == null ? null : o.toString();
	}

	/**
	 * 获取缓存
	 *
	 * @param key 键
	 * @return 值
	 */
	public static Object getObject(String key) {
		return redisTemplate.opsForValue().get(key);
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
		redisTemplate.opsForValue().set(key, value);
		if (cacheSeconds != 0) {
			redisTemplate.expire(key, cacheSeconds, TimeUnit.SECONDS);
		}
		return "OK";
	}

	/**
	 * 设置缓存
	 *
	 * @param key          键
	 * @param value        值
	 * @param cacheSeconds 超时时间，0为不超时
	 * @return
	 */
	public static String set(String key, Object value, int cacheSeconds) {
		redisTemplate.opsForValue().set(key, value);

		if (cacheSeconds != 0) {
			redisTemplate.expire(key, cacheSeconds, TimeUnit.SECONDS);
		}
		return "OK";
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
		redisTemplate.opsForValue().set(key, value);
		if (cacheSeconds != 0) {
			redisTemplate.expire(key, cacheSeconds, TimeUnit.SECONDS);
		}
		return "OK";
	}

	/**
	 * 获取List缓存
	 *
	 * @param key 键
	 * @return 值
	 */
	public static List<Object> getList(String key) {
		List<Object> list = redisTemplate.opsForList().range(key, 0, -1);
		return list;
	}


	/**
	 * 获取List缓存
	 *
	 * @param key 键
	 * @return 值
	 */
	public static List<Object> getObjectList(String key) {
		List<Object> list = redisTemplate.opsForList().range(key, 0, -1);
		return list;
	}


    /**
     * 设置List缓存
     *
     * @param key          键
     * @param value        值
     * @param cacheSeconds 超时时间，0为不超时
     * @return
     */
    public static long setStringList(String key, List<String> value, int cacheSeconds) {
        long result = 0;
        try {
            result = redisTemplate.opsForList().rightPushAll(key, value);
            redisTemplate.expire(key,cacheSeconds,TimeUnit.SECONDS);
        } catch (Exception e) {
            logger.warn("listObjectAdd {} = {}", key, value, e);
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
		try {
			result = redisTemplate.opsForList().rightPushAll(key, value);
            redisTemplate.expire(key,cacheSeconds,TimeUnit.SECONDS);
		} catch (Exception e) {
			logger.warn("listObjectAdd {} = {}", key, value, e);
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
		try {
			result = redisTemplate.opsForList().rightPushAll(key, value);

		} catch (Exception e) {
			logger.warn("listObjectAdd {} = {}", key, value, e);
		}
		return result;
	}


	/**
	 * 删除list元素
	 *
	 * @param key   键
	 * @param count 删几个（正数从头，负数从尾，0所有）
	 * @param value 值
	 * @return
	 */
	public static long listRemove(String key, long count,Object... value) {
		long result = 0;
		try {
			result = redisTemplate.opsForList().remove (key,count, value);

		} catch (Exception e) {
			logger.warn("listObjectAdd {} = {}", key, value, e);
		}
		return result;
	}

	/**
	 * 获取缓存
	 *
	 * @param key 键
	 * @return 值
	 */
	public static Set<Object> getObjectSet(String key) {
		Set<Object> value = null;
		try {
			value = redisTemplate.opsForSet().members(key);
		} catch (Exception e) {
			logger.warn("getObjectSet {} = {}", key, value, e);
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
	public static long setObjectSet(String key, Set<Object> value, int cacheSeconds) {
		long result = 0;
		try {
			result = redisTemplate.opsForSet().add(value);
            redisTemplate.expire(key,cacheSeconds,TimeUnit.SECONDS);
		} catch (Exception e) {
			logger.warn("setSet {} = {}", key, value, e);
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
	public static void setSetObjectAdd(String key, Object... value) {
		try {
			redisTemplate.opsForSet().add(key, value);
			logger.debug("setSetObjectAdd {} = {}", key, value);
		} catch (Exception e) {
			logger.warn("setSetObjectAdd {} = {}", key, value, e);
		}
	}

	/**
	 * 获取Map缓存
	 *
	 * @param key 键
	 * @return 值
	 */
	public static Map<Object, Object> getObjectMap(String key) {
		Map<Object, Object> value = null;
		try {
			value = redisTemplate.opsForHash().entries(key);
		} catch (Exception e) {
			logger.warn("getObjectMap {} = {}", key, value, e);
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
	public static void setObjectMap(String key, Map<String, Object> value, int cacheSeconds) {
		try {
			redisTemplate.opsForHash().putAll(key, value);
			redisTemplate.expire(key, cacheSeconds, TimeUnit.SECONDS);
			logger.debug("setObjectMap {} = {}", key, value);
		} catch (Exception e) {
			logger.warn("setObjectMap {} = {}", key, value, e);
		}
	}

	/**
	 * 向Map缓存中添加值
	 *
	 * @param key   键
	 * @param value 值
	 * @return
	 */
	public static void mapPut(String key, Map<String, String> value) {
		try {
			redisTemplate.opsForHash().putAll(key, value);
			logger.debug("mapObjectPut {} = {}", key, value);
		} catch (Exception e) {
			logger.warn("mapObjectPut {} = {}", key, value, e);
		}
	}


	

	/**
	 * 移除Map缓存中的值
	 * @param key 键
	 * @return
	 */
	public static long mapRemove(String key, String mapKey) {
		long result = 0;
		try {
			redisTemplate.opsForHash().delete(key,mapKey);
			logger.debug("mapObjectRemove {}  {}", key, mapKey);
		} catch (Exception e) {
			logger.warn("mapObjectRemove {}  {}", key, mapKey, e);
		}
		return result;
	}

	
	/**
	 * 判断Map缓存中的Key是否存在
	 * @param key 键
	 * @return
	 */
	public static boolean mapExists(String key, String mapKey) {
		boolean result = false;
		try {
			result=redisTemplate.opsForHash().hasKey(key,mapKey);
			logger.debug("mapObjectExists {}  {}", key, mapKey);
		} catch (Exception e) {
			logger.warn("mapObjectExists {}  {}", key, mapKey, e);
		}
		return result;
	}

	
	/**
	 * 删除缓存
	 * @param key 键
	 * @return
	 */
	public static void del(String key) {
		long result = 0;
		try {
			redisTemplate.delete(key);
		} catch (Exception e) {
			logger.warn("delObject {}", key, e);
		}
	}




	/**
	 * 模糊删除缓存
	 * @param prex 键
	 * @return
	 */
	public static void patternDel(String prex) {
		try {
			Set<Object> keys = redisTemplate.keys(prex);
			redisTemplate.delete(keys);
		} catch (Exception e) {
			logger.warn("del {}", prex, e);
		}

	}
	
	/**
	 * 缓存是否存在
	 * @param key 键
	 * @return
	 */
	public static boolean exists(String key) {
		return existsObject(key);
	}
	
	/**
	 * 缓存是否存在
	 * @param key 键
	 * @return
	 */
	public static boolean existsObject(String key) {
		boolean result = false;
		try {
			result=redisTemplate.hasKey(key);
			logger.debug("existsObject {}", key);
		} catch (Exception e) {
			logger.warn("existsObject {}", key, e);
		}
		return result;
	}


	private static final Long RELEASE_SUCCESS = 1L;
	private static final String LOCK_SUCCESS = "OK";
	private static final String SET_IF_NOT_EXIST = "NX";
	private static final String SET_WITH_EXPIRE_TIME = "PX";
	private static final String RELEASE_LOCK_SCRIPT = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";

	/**
	 * 该加锁方法仅针对单实例 Redis 可实现分布式加锁
	 * 对于 Redis 集群则无法使用
	 *
	 * 支持重复，线程安全
	 *
	 * @param lockKey   加锁键
	 * @param requestId  加锁客户端唯一标识
	 * @param seconds   锁过期时间
	 * @return
	 */

	public static Boolean tryGetDistributedLock(String lockKey, String requestId, long seconds) {
		return redisTemplate.execute((RedisCallback<Boolean>) redisConnection -> {
			Jedis jedis = (Jedis) redisConnection.getNativeConnection();
			String result = jedis.set(lockKey, requestId, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, seconds*1000);
			if (LOCK_SUCCESS.equals(result)) {
				return Boolean.TRUE;
			}
			return Boolean.FALSE;
		});
	}

	/**
	 * 与 tryLock 相对应，用作释放锁
	 *
	 * @param lockKey
	 * @param requestId
	 * @return
	 */

	public static Boolean releaseDistributedLock(String lockKey, String requestId) {
		return redisTemplate.execute((RedisCallback<Boolean>) redisConnection -> {
			Jedis jedis = (Jedis) redisConnection.getNativeConnection();
			Object result = jedis.eval(RELEASE_LOCK_SCRIPT, Collections.singletonList(lockKey), Collections.singletonList(requestId));
			if (RELEASE_SUCCESS.equals(result)) {
				return Boolean.TRUE;
			}
			return Boolean.FALSE;
		});
	}

}
