package cn.zeppin.product.ntb.backadmin.security;


import java.util.ArrayList;
import java.util.Collection;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import cn.zeppin.product.ntb.backadmin.service.api.IBkControllerMethodService;
import cn.zeppin.product.ntb.backadmin.service.api.IBkControllerService;
import cn.zeppin.product.ntb.backadmin.service.api.IBkOperatorRoleService;
import cn.zeppin.product.ntb.backadmin.service.api.IBkOperatorService;
import cn.zeppin.product.ntb.backadmin.service.api.IBkRoleControllerPermissionService;
import cn.zeppin.product.ntb.core.entity.QcbAdmin;
import cn.zeppin.product.ntb.qcb.service.api.IQcbAdminService;


/**
 * 用户身份验证,授权 Realm 组件
 * 
 * @author Clark.R
 * @since 2016年6月11日
 **/
//@Component(value = "securityRealm")
public class SecurityByQcbRealm extends AuthorizingRealm {

    @Autowired
    private IBkOperatorService bkOperatorService;
    
    @Autowired
    private IBkOperatorRoleService bkOperatorRoleService;
    
	@Autowired
	private IBkControllerService bkControllerService;
	
	@Autowired
	private IBkControllerMethodService bkControllerMethodService;
    
	@Autowired
	private IBkRoleControllerPermissionService bkRoleControllerPermissionService;
	
	@Autowired
	private IQcbAdminService qcbAdminService; 
    
    
    public SecurityByQcbRealm(CacheManager cacheManager, CredentialsMatcher matcher) {
    	super(cacheManager, matcher);
    }
    /**
     * 获取在SpringMVC Shiro配置文件中注入方式生成的SecurityRealm对象
     * @return
     */
    public static SecurityByQcbRealm getInstance() {
    	DefaultSecurityManager securityManager = (DefaultSecurityManager) SecurityUtils.getSecurityManager();
    	Collection<Realm> realms = securityManager.getRealms();
    	Collection<Realm> typeRealms = new ArrayList<>();
        for (Realm realm : realms) {
            if (realm.getName().contains(LoginType.QCB.toString()))
                typeRealms.add(realm);
        }
    	SecurityByQcbRealm realm = (SecurityByQcbRealm) typeRealms.iterator().next();
		return realm;
    }
    
    /**
     * 根据SpringMVC Shiro配置文件中的加密算法进行字符串加密，主要用于密码加密
     * @param password
     * @return
     */
    public static String encrypt(Object source, ByteSource salt) {
    	HashedCredentialsMatcher credentialsMatcher = (HashedCredentialsMatcher) SecurityByQcbRealm.getInstance().getCredentialsMatcher();
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
//	        String username = (String) getAvailablePrincipal(principals);  
	        
//	        final BkOperator bkOperator = bkOperatorService.getByLoginname(username);
//	        final BkOperatorRole role = bkOperatorRoleService.get(bkOperator.getRole());
//	        final List<BkRoleControllerPermission> permissions = bkRoleControllerPermissionService.getByRole(role);
//	        //向已登录用户添加角色授权
//	        authorizationInfo.addRole(role.getName());
//	        for (BkRoleControllerPermission permission : permissions) {
//	        	BkController controller = bkControllerService.get(permission.getController());
//	        	BkControllerMethod method = bkControllerMethodService.get(permission.getMethod());
//	        	String strPermission = controller.getName() + ":" + method.getName();
//	        	//向已登录用户添加角色功能权限授权
//	        	authorizationInfo.addStringPermission(strPermission);
//	        }

        return authorizationInfo;
    }

    /**
     * 登录验证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
//		Subject subject = SecurityUtils.getSubject();
//		Session session = subject.getSession();
		
    	//token中储存着输入的用户名和密码  
    	CustomizedToken upToken = (CustomizedToken)token;  
        upToken.setRememberMe(true);
    	
        //获得用户名与密码  
        String loginname = upToken.getUsername();  
//        String password = String.valueOf(upToken.getPassword()); 
        
        
        // 通过数据库进行验证
//        final BkOperator bkOperator =  bkOperatorService.getByLoginname(loginname);
        final QcbAdmin bkOperator = qcbAdminService.getByMobile(loginname);
        if (bkOperator == null) {
            throw new UnknownAccountException(); 
        }  
        
//        // 判断帐号是否锁定  
//        else if (BkOperatorStatus.LOCKED.equals(bkOperator.getStatus())) {  
//            // 抛出 帐号锁定异常  
//            throw new LockedAccountException(); 
//        }  

        	/*  
        	 *  关键认证信息，依靠RetryNumLimitCredentialsMatcher做密码加密认证
        	 */
            SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
            		bkOperator.getName(), bkOperator.getPassword(), ByteSource.Util.bytes(bkOperator.getUuid()), getName());
            
        	return authenticationInfo;
 

    }
 
    /**
     * 登录代码样例
     */
//  //创建用户名和密码的令牌  
//    UsernamePasswordToken token = new UsernamePasswordToken(user.getUserName(),user.getPassWord());  
//    //记录该令牌，如果不记录则类似购物车功能不能使用。  
//    token.setRememberMe(true);  
//    //subject理解成权限对象。类似user  
//    Subject subject = SecurityUtils.getSubject();  
//    try {  
//    subject.login(token);  
//    } catch (UnknownAccountException ex) {//用户名没有找到。  
//    } catch (IncorrectCredentialsException ex) {//用户名密码不匹配。  
//    }catch (AuthenticationException e) {//其他的登录错误  
//    }  
//    //验证是否成功登录的方法  
//    if (subject.isAuthenticated()) {  
//    }  
    
    /**
     * 登出代码样例
     */
//    Subject subject = SecurityUtils.getSubject();  
//    subject.logout();  
    
    
    /**
     * 基于编码的角色授权实现
     */
//    Subject currentUser = SecurityUtils.getSubject();    
//    if (currentUser.hasRole("administrator")) {    
//        //拥有角色administrator  
//    } else {    
//        //没有角色处理  
//    }        
    
    
    /**
     * 断言方式控制
     */
//    Subject currentUser = SecurityUtils.getSubject();    
//    //如果没有角色admin，则会抛出异常，someMethod()也不会被执行  
//    currentUser.checkRole(“admin");    
//    someMethod();    
    
    
    /**
     * 基于编码的资源授权实现
     */
//    Subject currentUser = SecurityUtils.getSubject();    
//    if (currentUser.isPermitted("permssion:look")) {    
//        //有资源权限  
//    } else {    
//        //没有权限  
//    }    
    /**
     * 断言方式控制
     */
//    Subject currentUser = SecurityUtils.getSubject();    
//    //如果没有资源权限则会抛出异常。  
//    currentUser.checkPermission("permssion:look");    	
//    someMethod();    
    
    /**
     * JSP 标签实现
     */
//    <shiro:authenticated>
//
//    登录之后
//
//    <shiro:notAuthenticated>
//
//    不在登录状态时
//
//    <shiro:guest>
//
//    用户在没有RememberMe时
//
//    <shiro:user>
//
//    用户在RememberMe时
//
//    <shiro:hasAnyRoles name="abc,123" >
//
//    在有abc或者123角色时
//
//    <shiro:hasRole name="abc">
//
//    拥有角色abc
//
//    <shiro:lacksRole name="abc">
//
//    没有角色abc
//
//    <shiro:hasPermission name="abc">
//
//    拥有权限资源abc
//
//    <shiro:lacksPermission name="abc">
//
//    没有abc权限资源
//
//    <shiro:principal>
//
//    默认显示用户名称
    

}
