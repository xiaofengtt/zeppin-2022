package cn.product.payment.shiro;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;

import cn.product.payment.control.TransferService;
import cn.product.payment.controller.base.DataResult;
import cn.product.payment.controller.base.InputParams;
import cn.product.payment.entity.Admin;
import cn.product.payment.entity.Admin.AdminStatus;
import cn.product.payment.entity.Method;

public class ShiroSystemRealm extends AuthorizingRealm {

	@Autowired
    private TransferService transferService;

    @Autowired
    private RedisSessionDAO redisSessionDAO;

    //授权
    @Override
    @SuppressWarnings("unchecked")
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
    	String username = (String) SecurityUtils.getSubject().getPrincipal();
    	InputParams aparams = new InputParams("systemAdminService", "getByUsername");
    	aparams.addParams("username", null, username);
    	DataResult<Object> aresult = (DataResult<Object>) this.transferService.execute(aparams);
    	Admin admin = (Admin) aresult.getData();
    	
        InputParams params = new InputParams("systemRoleMethodService", "getMethodListByRole");
		params.addParams("role", null, admin.getRole());
        DataResult<Object> result = (DataResult<Object>) this.transferService.execute(params);
        List<Method> methodList = null;
        if(result.getData() != null) {
        	methodList = (List<Method>) result.getData();
        }
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        for(Method method: methodList){
            info.addStringPermission(method.getUuid());
        }
        return info;
    }

    //认证
    @Override
    @SuppressWarnings("unchecked")
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //获取用户的输入的账号.
    	CustomizedToken upToken = (CustomizedToken)token;  
        String username = (String)upToken.getPrincipal();
        
        InputParams params = new InputParams("systemAdminService", "getByUsername");
		params.addParams("username", null, username);
        DataResult<Object> result = (DataResult<Object>) this.transferService.execute(params);
        Admin admin = null;
        if(result.getData() != null) {
        	admin = (Admin) result.getData();
        }
        
        if(admin==null) throw new UnknownAccountException();
        if (!AdminStatus.NORMAL.equals(admin.getStatus())) {
            throw new LockedAccountException(); // 帐号不可用
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
        		admin.getUsername(), //用户
        		admin.getPassword(), //密码
                ByteSource.Util.bytes(username),
                getName()  //realm name
        );
        return authenticationInfo;
    }
    
    public static ShiroSystemRealm getInstance() {
    	DefaultSecurityManager securityManager = (DefaultSecurityManager) SecurityUtils.getSecurityManager();
    	Collection<Realm> realms = securityManager.getRealms();
    	Collection<Realm> typeRealms = new ArrayList<>();
        for (Realm realm : realms) {
            if (realm.getName().contains(LoginType.SYSTEM.toString()))
                typeRealms.add(realm);
        }
        ShiroSystemRealm realm = (ShiroSystemRealm) typeRealms.iterator().next();
		return realm;
    }
    
    //获取用户加密密码
    public static String encrypt(Object source, ByteSource salt) {
    	HashedCredentialsMatcher credentialsMatcher = (HashedCredentialsMatcher) ShiroSystemRealm.getInstance().getCredentialsMatcher();
    	String hashAlgorithm = credentialsMatcher.getHashAlgorithmName();
    	int iterations = credentialsMatcher.getHashIterations();
    	SimpleHash hash = new SimpleHash(hashAlgorithm, source, salt, iterations);
    	return hash.toString();
    }
}
