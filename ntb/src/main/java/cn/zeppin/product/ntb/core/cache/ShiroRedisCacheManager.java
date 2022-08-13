package cn.zeppin.product.ntb.core.cache;


import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.springframework.data.redis.cache.RedisCacheManager;

public class ShiroRedisCacheManager implements CacheManager {

	protected RedisCacheManager cacheManager;
	

	public RedisCacheManager getCacheManager() {
		return cacheManager;
	}

	public void setCacheManager(RedisCacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}

	
	public <K, V> Cache<K, V> getCache(String name) throws CacheException {
		ShiroRedisCache<K, V> cache =  new ShiroRedisCache<K, V>(cacheManager.getCache(name));
		return cache;
	}

}
