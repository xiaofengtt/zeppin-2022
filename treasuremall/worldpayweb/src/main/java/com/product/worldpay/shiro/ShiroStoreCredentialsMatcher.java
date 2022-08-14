/**
 * 
 */
package com.product.worldpay.shiro;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;

/**
 * @author Clark.R 2016年9月21日
 *
 */
public class ShiroStoreCredentialsMatcher extends HashedCredentialsMatcher {

	private Cache<String, Integer> passwordRetryNumbersCache;  

	/**
	 * 
	 * @param cacheManager
	 */
	public ShiroStoreCredentialsMatcher(CacheManager cacheManager) {  
		passwordRetryNumbersCache = cacheManager.getCache("passwordRetryCache");  
	}  
}
