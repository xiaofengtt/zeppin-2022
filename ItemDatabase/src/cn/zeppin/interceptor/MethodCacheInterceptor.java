/**
 * 
 */
package cn.zeppin.interceptor;

import java.io.Serializable;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

/**
 * @author Clark
 *
 */
public class MethodCacheInterceptor implements MethodInterceptor, InitializingBean {
	private static final Log logger = LogFactory.getLog(MethodCacheInterceptor.class);   
	private Cache cache;  
	
	
	/**  
     * implement InitializingBean，检查cache是否为空  
     */  
	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		 Assert.notNull(cache, "缓存对象为空，请创建一个缓存对象!");   
	}

	public Cache getCache() {
		return cache;
	}

	public void setCache(Cache cache) {
		this.cache = cache;
	}
	
	/**  
     * 拦截Service/DAO的方法，并查找该结果是否存在，如果存在就返回cache中的值，  
     * 否则，返回数据库查询结果，并将查询结果放入cache  
     */  
	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		// TODO Auto-generated method stub
		String targetName = invocation.getThis().getClass().getName();   
        String methodName = invocation.getMethod().getName();   
        Object[] arguments = invocation.getArguments();   
        Object result;   
       
        logger.debug("从缓存中查找数据：" + cache.getName());   
           
        String cacheKey = getCacheKey(targetName, methodName, arguments);   
        Element element = cache.get(cacheKey);   
  
        if (element == null || element.isExpired()) {   
            logger.debug("缓存没有命中 , 开始执行对库查询：" + cacheKey);
            System.out.println(cacheKey);
            result = invocation.proceed();   
            element = new Element(cacheKey, (Serializable) result);   
            cache.put(element);   
        }   
        return element.getObjectValue();   
	}

    /**  
     * 获得cache key的方法，cache key是Cache中一个Element的唯一标识  
     * cache key包括 包名+类名+方法名+参数，如cn.zeppin.service.imp.UserServiceImpl.getAllUser  
     */  
    private String getCacheKey(String targetName, String methodName, Object[] arguments) {   
        StringBuffer sb = new StringBuffer();   
        sb.append(targetName).append(".").append(methodName);   
        if ((arguments != null) && (arguments.length != 0)) {   
            for (int i = 0; i < arguments.length; i++) {   
                sb.append(".").append(arguments[i]);   
            }   
        }   
        return sb.toString();   
    }   
}
