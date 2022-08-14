package cn.zeppin.product.itic.core.cache;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.util.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.data.redis.core.RedisTemplate;


public class ShiroRedisCache<K, V> implements Cache<K, V> {
	
	private static final Logger log = LoggerFactory.getLogger(ShiroRedisCache.class);
	
	private org.springframework.cache.Cache cache;
	

	
	public ShiroRedisCache(org.springframework.cache.Cache cache) {
        if (cache == null) {
            throw new IllegalArgumentException("Cache argument cannot be null.");
        }
        this.cache = cache;
	}
	

	@SuppressWarnings({"unchecked"})
	@Override
	public V get(K key) throws CacheException {

		try {
			
            if (log.isTraceEnabled()) {
                log.trace("Getting object from shiroRedisCache [" + cache.getName() + "] for key [" + key + "]");
            }
            if (key == null) {
                return null;
            } else {
            	ValueWrapper valueWrapper = cache.get(key);
                if (valueWrapper == null) {
                    if (log.isTraceEnabled()) {
                        log.trace("Element for [" + key + "] is null.");
                    }
                    return null;
                } else {
                    //noinspection unchecked
                    return (V) valueWrapper.get();
                }
            }
        } catch (Throwable t) {
            throw new CacheException(t);
        }
	}
	
	
	@Override
	public V put(K key, V value) throws CacheException {
        if (log.isTraceEnabled()) {
            log.trace("Putting object in cache [" + cache.getName() + "] for key [" + key + "]");
        }
        try {
        	V previous = this.get(key);
            cache.put(key, value);
            return previous;
        } catch (Throwable t) {
            throw new CacheException(t);
        }
	}

	@SuppressWarnings({"unchecked"})
	@Override
	public V remove(K key) throws CacheException {
        if (log.isTraceEnabled()) {
            log.trace("Removing object from cache [" + cache.getName() + "] for key [" + key + "]");
        }
        try {
            V previous = this.get(key);
            RedisTemplate<K,V> redisTemplate = (RedisTemplate<K,V>) cache.getNativeCache();
            redisTemplate.delete(key);
            return previous;
        } catch (Throwable t) {
            throw new CacheException(t);
        }
	}

	@Override
	public void clear() throws CacheException {
        if (log.isTraceEnabled()) {
            log.trace("Clearing all objects from cache [" + cache.getName() + "]");
        }
        try {
            cache.clear();
        } catch (Throwable t) {
            throw new CacheException(t);
        }
	}

	@SuppressWarnings("unchecked")
	@Override
	public int size() {
        try {
			RedisTemplate<K,V> redisTemplate = (RedisTemplate<K,V>) cache.getNativeCache();
        	return redisTemplate.keys((K) "*").size();
        } catch (Throwable t) {
            throw new CacheException(t);
        }
	}

	@SuppressWarnings({"unchecked"})
	@Override
	public Set<K> keys() {
        try {
            
            RedisTemplate<K,V> redisTemplate = (RedisTemplate<K,V>) cache.getNativeCache();
            Set<K> keys = redisTemplate.keys((K) "*");
            if (!CollectionUtils.isEmpty(keys)) {
                return keys;
            } else {
                return Collections.emptySet();
            }
        } catch (Throwable t) {
            throw new CacheException(t);
        }
	}

	@SuppressWarnings({"unchecked"})
	@Override
	public Collection<V> values() {
        try {
        	RedisTemplate<K,V> redisTemplate = (RedisTemplate<K,V>) cache.getNativeCache();
        	Set<K> keys = redisTemplate.keys((K) "*");
            if (!CollectionUtils.isEmpty(keys)) {
                List<V> values = new ArrayList<V>(keys.size());
                for (K key : keys) {
                    V value = get(key);
                    if (value != null) {
                        values.add(value);
                    }
                }
                return Collections.unmodifiableList(values);
            } else {
                return Collections.emptyList();
            }
        } catch (Throwable t) {
            throw new CacheException(t);
        }
	}


	
	

}
