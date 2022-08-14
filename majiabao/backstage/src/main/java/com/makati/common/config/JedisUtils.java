package com.makati.common.config;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.concurrent.TimeUnit;
@Slf4j
@Service
public class JedisUtils {

	private static Logger logger = LoggerFactory.getLogger(JedisUtils.class);





	@Resource
	private RedisTemplate redisTemplate;

//	private static RedisTemplate<Object,Object> redisTemplate = SpringContextHolder.getBean("redisTemplate");


	public void delete(String key){
		redisTemplate.delete(key);
	}

	/**
	 * 获取缓存
	 * @param key 键
	 * @return 值
	 */
	public   String get(String key) {
		Object o = redisTemplate.opsForValue().get(key);
		return o == null ? null : o.toString();
	}



	/**
	 * 获取缓存
	 * @param key 键
	 * @return 值
	 */
	public  Object getObject(String key) {
		return  redisTemplate.opsForValue().get(key);
	}
	
	/**
	 * 设置缓存
	 * @param key 键
	 * @param value 值
	 * @param cacheSeconds 超时时间，0为不超时
	 * @return
	 */
	public  String set(String key, String value, int cacheSeconds) {
		redisTemplate.opsForValue().set(key, value);
		if (cacheSeconds > 0) {
			redisTemplate.expire(key,cacheSeconds, TimeUnit.SECONDS);
		}
		return "OK";
	}

	/**
	 * 设置缓存
	 * @param key 键
	 * @param value 值
	 * @param cacheSeconds 超时时间，0为不超时
	 * @return
	 */
	public  String set(String key, Object value, int cacheSeconds) {
		redisTemplate.opsForValue().set(key, value);
		if (cacheSeconds > 0) {
			redisTemplate.expire(key,cacheSeconds, TimeUnit.SECONDS);
		}
		return "OK";
	}
	
	/**
	 * 设置缓存
	 * @param key 键
	 * @param value 值
	 * @param cacheSeconds 超时时间，0为不超时
	 * @return
	 */
	public  String setObject(String key, Object value, int cacheSeconds) {
		redisTemplate.opsForValue().set(key, value);
		if (cacheSeconds > 0) {
			redisTemplate.expire(key,cacheSeconds, TimeUnit.SECONDS);
		}
		return "OK";
	}

    /**
     * 删除缓存
     * @param key 键
     * @return
     */
    public  void del(String key) {
        long result = 0;
        try {
            redisTemplate.delete(key);
        } catch (Exception e) {
            logger.warn("delObject {}", key, e);
        }
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

	public  Boolean tryGetDistributedLock(String lockKey, String requestId, long seconds) {
		return (Boolean) redisTemplate.execute((RedisCallback<Boolean>) redisConnection -> {
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

	public  Boolean releaseDistributedLock(String lockKey, String requestId) {
		return (Boolean) redisTemplate.execute((RedisCallback<Boolean>) redisConnection -> {
			Jedis jedis = (Jedis) redisConnection.getNativeConnection();
			Object result = jedis.eval(RELEASE_LOCK_SCRIPT, Collections.singletonList(lockKey), Collections.singletonList(requestId));
			if (RELEASE_SUCCESS.equals(result)) {
				return Boolean.TRUE;
			}
			return Boolean.FALSE;
		});
	}

}
