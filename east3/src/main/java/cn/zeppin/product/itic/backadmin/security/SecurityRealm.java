package cn.zeppin.product.itic.backadmin.security;


import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import cn.zeppin.product.itic.backadmin.service.api.ITSsControllerMethodService;
import cn.zeppin.product.itic.backadmin.service.api.ITSsControllerService;
import cn.zeppin.product.itic.backadmin.service.api.ITSsOperatorMethodService;
import cn.zeppin.product.itic.backadmin.service.api.ITSsOperatorService;
import cn.zeppin.product.itic.backadmin.service.api.ITSsRoleService;
import cn.zeppin.product.itic.core.entity.TSsController;
import cn.zeppin.product.itic.core.entity.TSsControllerMethod;
import cn.zeppin.product.itic.core.entity.TSsOperator;
import cn.zeppin.product.itic.core.entity.TSsOperatorMethod;
import cn.zeppin.product.itic.core.entity.TSsRole;


/**
 * 用户身份验证,授权 Realm 组件
 * 
 **/
public class SecurityRealm extends AuthorizingRealm {
	
	@Autowired
	private ITSsOperatorService tSsOperatorService;
	
	@Autowired
	private ITSsRoleService tSsRoleService;
	
	@Autowired
	private ITSsControllerService tSsControllerService;
	
	@Autowired
	private ITSsControllerMethodService tSsControllerMethodService;
	
	@Autowired
	private ITSsOperatorMethodService tSsOperatorMethodService;
	
    public SecurityRealm(CacheManager cacheManager, CredentialsMatcher matcher) {
    	super(cacheManager, matcher);
    }
    /**
     * 获取在SpringMVC Shiro配置文件中注入方式生成的SecurityRealm对象
     * @return
     */
    public static SecurityRealm getInstance() {
    	DefaultSecurityManager securityManager = (DefaultSecurityManager) SecurityUtils.getSecurityManager();
    	SecurityRealm realm = (SecurityRealm) securityManager.getRealms().iterator().next();
		return realm;
    }
    
    /**
     * 根据SpringMVC Shiro配置文件中的加密算法进行字符串加密，主要用于密码加密
     * @param password
     * @return
     */
    public static String encrypt(Object source, ByteSource salt) {
    	HashedCredentialsMatcher credentialsMatcher = (HashedCredentialsMatcher) SecurityRealm.getInstance().getCredentialsMatcher();
    	String hashAlgorithm = credentialsMatcher.getHashAlgorithmName();
    	int iterations = credentialsMatcher.getHashIterations();
    	SimpleHash hash = new SimpleHash(hashAlgorithm, source, salt, iterations);
    	return hash.toString();
    }
    
	/**
     * 用户授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
    	SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        String username = (String) getAvailablePrincipal(principals);  
        
    	final TSsOperator operator = tSsOperatorService.getByLoginname(username);
        final TSsRole role = tSsRoleService.get(operator.getRole());
        final List<TSsOperatorMethod> permissions = tSsOperatorMethodService.getByOperator(operator);
        //向已登录用户添加角色授权
        authorizationInfo.addRole(role.getName());
        for (TSsOperatorMethod permission : permissions) {
        	TSsController controller = tSsControllerService.get(permission.getController());
        	TSsControllerMethod method = tSsControllerMethodService.get(permission.getMethod());
        	String strPermission = controller.getName() + ":" + method.getName();
        	//向已登录用户添加角色功能权限授权
        	authorizationInfo.addStringPermission(strPermission);
	        }

        return authorizationInfo;
    }

    /**
     * 登录验证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
    	//token中储存着输入的用户名和密码  
    	UsernamePasswordToken upToken = (UsernamePasswordToken)token;  
        upToken.setRememberMe(true);
        
        //获得用户名与密码  
        String loginname = upToken.getUsername();  
        
        // 通过数据库进行验证
        final TSsOperator operator =  tSsOperatorService.getByLoginname(loginname);
        if (operator == null) {
            throw new UnknownAccountException();
        }  

    	//关键认证信息，依靠RetryNumLimitCredentialsMatcher做密码加密认证
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(operator.getCode(), operator.getPassword(), ByteSource.Util.bytes(operator.getId()), getName());
        
    	return authenticationInfo;
 
    }
    
    /**
     * 清空权限
     */
    public void clearAuthz(){  
        this.clearCachedAuthorizationInfo(SecurityUtils.getSubject().getPrincipals());  
    } 
 
}
