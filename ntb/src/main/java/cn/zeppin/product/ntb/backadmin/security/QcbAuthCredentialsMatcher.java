/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.security;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;

/**
 * @author Clark.R 2016年9月21日
 *
 */
public class QcbAuthCredentialsMatcher extends HashedCredentialsMatcher {

	private Cache<String, Integer> passwordRetryNumbersCache;  

	/**
	 * 
	 * @param cacheManager
	 */
	public QcbAuthCredentialsMatcher(CacheManager cacheManager) {  
		passwordRetryNumbersCache = cacheManager.getCache("passwordRetryCache");  
	}  
	
	@Override  
	public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
//		UsernamePasswordToken upToken = (UsernamePasswordToken)token;  
		//不能用(String) upToken.getUsername(); 因登录username可能是name，mobile，email的任意一种，作为cache key不唯一
		String username = (String) info.getPrincipals().getPrimaryPrincipal();
//		String password = String.valueOf(upToken.getPassword()); 
		//内存中取出计数器
		boolean matches = super.doCredentialsMatch(token, info);
		
		//正确匹配清零计数器
		if (matches) {
			// clear retry count
			passwordRetryNumbersCache.remove(username);
		}
		else {			
			throw new IncorrectCredentialsException("用户名或密码输入错误！");  
		}
		return matches;
	}
}
