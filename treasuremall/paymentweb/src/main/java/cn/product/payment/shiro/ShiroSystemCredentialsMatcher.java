/**
 * 
 */
package cn.product.payment.shiro;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;

/**
 * @author Clark.R 2016年9月21日
 *
 */
public class ShiroSystemCredentialsMatcher extends HashedCredentialsMatcher {

	private Cache<String, Integer> passwordRetryNumbersCache;  

	/**
	 * 
	 * @param cacheManager
	 */
	public ShiroSystemCredentialsMatcher(CacheManager cacheManager) {  
		passwordRetryNumbersCache = cacheManager.getCache("passwordRetryCache");  
	}  
	
}
