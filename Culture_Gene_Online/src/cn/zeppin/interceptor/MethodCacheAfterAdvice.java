package cn.zeppin.interceptor;

import java.lang.reflect.Method;
import java.util.List;

import net.sf.ehcache.Cache;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

/**
 * 在用户进行create/update/delete等操作时来刷新/remove相关cache内容 
 */
public class MethodCacheAfterAdvice implements AfterReturningAdvice, InitializingBean {
	private static final Log logger = LogFactory.getLog(MethodCacheAfterAdvice.class);   
	public Cache cache;  
	
	public Cache getCache() {
		return cache;
	}

	public void setCache(Cache cache) {
		this.cache = cache;
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(cache, "Need a cache. Please use setCache(Cache) create it.");   
	}

	@Override
	@SuppressWarnings("rawtypes")
	public void afterReturning(Object arg0, Method arg1, Object[] arg2,
			Object arg3) throws Throwable {
		 String className = arg3.getClass().getName();   
			List list = cache.getKeys();   
	        for(int i = 0;i<list.size();i++){   
	            String cacheKey = String.valueOf(list.get(i));   
	            if(cacheKey.startsWith(className)){   
	                cache.remove(cacheKey);   
	                logger.debug("remove cache " + cacheKey);   
	            }   
	        }   
	}

}
