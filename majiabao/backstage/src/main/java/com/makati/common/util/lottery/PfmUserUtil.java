package com.makati.common.util.lottery;

import com.makati.common.config.JedisUtils;
import com.makati.common.entity.PlatformUser;
import com.makati.common.exception.BusinessException;
import com.makati.constant.ConstantUtil;
import com.makati.constant.Rcode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class PfmUserUtil {

	@Autowired
	private RedisTemplate redisTemplate;

	/**
	 * 用户存放在缓存里的前缀
	 */
	public static final String JEDIS_USER_PREFIX="user_";

	public static final String ONLINE_JEDIS_USER_PREFIX="online_";

	@Autowired
    JedisUtils jedisUtils;

	/**
	 * 用户标识
	 */
	public static final String USER_TOKEN="user_token";

	/**
	 * 从jedis缓存获取用户，用户不一定存在
	 * @param user_token
	 * @return
	 */
	public PlatformUser getUserFromCacheJedis(String user_token) throws Exception{
		ValueOperations valueOperations = redisTemplate.opsForValue();
		Object obj = valueOperations.get(JEDIS_USER_PREFIX+user_token);
		if(obj==null){
			throw new BusinessException(ErrorCodeUtil.SESSION_INVALID_ERROR_CODE,"会话失效");
		}
		PlatformUser user=(PlatformUser) obj;
//		JedisUtils.set(ONLINE_JEDIS_USER_PREFIX+user.getUser_id(), user_token,60*30);
		valueOperations.set(ONLINE_JEDIS_USER_PREFIX+user.getUserId(), user_token);
		redisTemplate.expire(ONLINE_JEDIS_USER_PREFIX+user.getUserId(),30, TimeUnit.MINUTES);
		return user;
	}

	/**
	 * 根据用户标识，从当前的缓存里获取用户
	 * @param user_token
	 * @return
	 */
	public  PlatformUser getUserFromCache(String user_token) throws BusinessException{
		ValueOperations valueOperations = redisTemplate.opsForValue();
		log.info("redisToken========="+user_token);
		//Object obj = valueOperations.get(JEDIS_USER_PREFIX+user_token);
        Object obj = jedisUtils.getObject(ConstantUtil.JEDIS_USER_PREFIX_GAME+user_token);
		if(obj==null){
			throw new BusinessException(Rcode.invalid_user,"用户无效或过期，请重新登录");
		}
		PlatformUser user=(PlatformUser) obj;
//		JedisUtils.set(ONLINE_JEDIS_USER_PREFIX+user.getUser_id(), user_token,60*30);
		valueOperations.set(ONLINE_JEDIS_USER_PREFIX+user.getUserId(), user_token);
		redisTemplate.expire(ONLINE_JEDIS_USER_PREFIX+user.getUserId(),30, TimeUnit.MINUTES);
		return user;
	}

//	/**
//	 * 根据用户标识，从当前的缓存里获取用户
//	 * @param user_token
//	 * @return
//	 */
//	public static PlatformUser getUserFromCache(String user_token) throws Exception{
////		Object obj=JedisUtils.getObject(JEDIS_USER_PREFIX+user_token);
//		Object obj = JedisUtils.getObject(JEDIS_USER_PREFIX+user_token);
//		PlatformUser user=null;
//		if(obj!=null){
//			user = (PlatformUser) obj;
//			JedisUtils.set(ONLINE_JEDIS_USER_PREFIX+user.getUser_id(), user_token,60*30);
//		}else {
//			throw new BusinessException(ErrorCodeUtil.SESSION_INVALID_ERROR_CODE, "获取用户信息出错");
//		}
//		return user;
//	}

//	/**
//	 * 从jedis缓存获取用户，用户不一定存在
//	 * @param user_token
//	 * @return
//	 */
//	public static PlatformUser getUserFromCacheJedis(String user_token) throws Exception{
//		Object obj=JedisUtils.getObject(JEDIS_USER_PREFIX+user_token);
//		if(obj==null){
//			throw new BusinessException(ErrorCodeUtil.SESSION_INVALID_ERROR_CODE);
//		}
//		PlatformUser user=(PlatformUser) obj;
//		JedisUtils.set(ONLINE_JEDIS_USER_PREFIX+user.getUser_id(), user_token,60*30);
//		return user;
//	}

//	public static Object getUserFromCacheJedisForterceptor(String user_token) throws Exception{
//		Object obj=JedisUtils.getObject(JEDIS_USER_PREFIX+user_token);
//		if (obj != null){
//			PlatformUser user=(PlatformUser) obj;
//			JedisUtils.set(ONLINE_JEDIS_USER_PREFIX+user.getUser_id(), user_token,60*30);
//		}
//		return obj;
//	}

//	/**
//	 * 根据用户标识，从当前的缓存里获取用户
//	 * @param user_token
//	 * @return
//	 */
//	public static PlatformUser getUserFromCache(String user_token){
//		PlatformUser user = (PlatformUser) EhCacheUtils.getAndFlush(EhCacheUtils.DEFAULT_CACHE,user_token);
//		return user;
//	}
}
