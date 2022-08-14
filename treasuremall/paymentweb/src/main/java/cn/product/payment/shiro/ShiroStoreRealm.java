package cn.product.payment.shiro;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
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
import cn.product.payment.entity.CompanyAdmin;
import cn.product.payment.entity.CompanyAdmin.CompanyAdminStatus;

public class ShiroStoreRealm extends AuthorizingRealm {

	@Autowired
    private TransferService transferService;

    @Autowired
    private RedisSessionDAO redisSessionDAO;

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    //认证
    @Override
    @SuppressWarnings("unchecked")
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //获取用户的输入的账号.
    	CustomizedToken upToken = (CustomizedToken)token;  
        String username = (String)upToken.getPrincipal();
        
        InputParams params = new InputParams("storeCompanyAdminService", "getByMobile");
		params.addParams("mobile", null, username);
        DataResult<Object> result = (DataResult<Object>) this.transferService.execute(params);
        CompanyAdmin ca = null;
        if(result.getData() != null) {
        	ca = (CompanyAdmin) result.getData();
        }
        if(ca==null) throw new UnknownAccountException();
        if (!CompanyAdminStatus.NORMAL.equals(ca.getStatus())) {
            throw new LockedAccountException(); // 帐号不可用
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
        		ca.getMobile(), //用户
        		ca.getPassword(), //密码
                ByteSource.Util.bytes(username),
                getName()  //realm name
        );
        return authenticationInfo;
    }
    
    public static ShiroStoreRealm getInstance() {
    	DefaultSecurityManager securityManager = (DefaultSecurityManager) SecurityUtils.getSecurityManager();
    	Collection<Realm> realms = securityManager.getRealms();
    	Collection<Realm> typeRealms = new ArrayList<>();
        for (Realm realm : realms) {
            if (realm.getName().contains(LoginType.STORE.toString()))
                typeRealms.add(realm);
        }
        ShiroStoreRealm realm = (ShiroStoreRealm) typeRealms.iterator().next();
		return realm;
    }
    
    //获取用户加密密码
    public static String encrypt(Object source, ByteSource salt) {
    	HashedCredentialsMatcher credentialsMatcher = (HashedCredentialsMatcher) ShiroStoreRealm.getInstance().getCredentialsMatcher();
    	String hashAlgorithm = credentialsMatcher.getHashAlgorithmName();
    	int iterations = credentialsMatcher.getHashIterations();
    	SimpleHash hash = new SimpleHash(hashAlgorithm, source, salt, iterations);
    	return hash.toString();
    }
}
