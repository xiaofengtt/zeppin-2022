/**
 * 
 */
package cn.zeppin.product.itic.backadmin.security;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;

import cn.zeppin.product.itic.core.exception.CustomExcessiveAttemptsException;

public class RetryNumLimitCredentialsMatcher extends HashedCredentialsMatcher {

	private Cache<String, Integer> passwordRetryNumbersCache;  

	/**
	 * 
	 * @param cacheManager
	 */
	public RetryNumLimitCredentialsMatcher(CacheManager cacheManager) {  
		passwordRetryNumbersCache = cacheManager.getCache("passwordRetryCache");  
	}  
	
	@Override  
	public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
		String username = (String) info.getPrincipals().getPrimaryPrincipal();
//		String password = String.valueOf(upToken.getPassword()); 
		//内存中取出计数器
		Integer retryCount = passwordRetryNumbersCache.get(username);
		//没错过即创建
		if (retryCount == null) {
			retryCount = 0;
		}
		retryCount = retryCount + 1;
		passwordRetryNumbersCache.put(username, retryCount);
		if (retryCount >= 4) {
			// if retry count >= 4 throw
			//如果锁定了，解锁需要在后台完成，需要将redis里面的计数器也清除
			throw new CustomExcessiveAttemptsException(retryCount, "账户密码输入错误超过4次，账户已锁定!");
		}

		boolean matches = super.doCredentialsMatch(token, info);
		
		//正确匹配清零计数器
		if (matches) {
			// clear retry count
			passwordRetryNumbersCache.remove(username);
		}
		else {			
			int iLastCount = 4 - retryCount;
			throw new IncorrectCredentialsException("密码输入错误, 还剩" + iLastCount + "次!");  
		}
		return matches;
	}
}
