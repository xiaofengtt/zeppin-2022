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
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.apache.shiro.util.ByteSource;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;

import cn.product.payment.entity.Admin;
import cn.product.payment.entity.Method;
import cn.product.payment.entity.Admin.AdminStatus;
import cn.product.payment.service.AdminService;
import cn.product.payment.service.MethodService;

public class ShiroCompanyRealm extends AuthorizingRealm {

    @Autowired
    private AdminService adminService;

    @Autowired
    private MethodService methodService;

    @Autowired
    private RedisSessionDAO redisSessionDAO;

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        Admin admin= (Admin) SecurityUtils.getSubject().getPrincipal();
        List<Method> methodList = methodService.getListByAdmin(admin.getUuid());
        // 权限信息对象info,用来存放查出的用户的所有的角色（role）及权限（permission）
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        for(Method method: methodList){
            info.addStringPermission(method.getUrl());
        }
        return info;
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //获取用户的输入的账号.
    	UsernamePasswordToken upToken = (UsernamePasswordToken)token;  
        String username = (String)upToken.getPrincipal();
        
        Admin admin = adminService.getByUsername(username);
        if(admin==null) throw new UnknownAccountException();
        if (!AdminStatus.NORMAL.equals(admin.getStatus())) {
            throw new LockedAccountException(); // 帐号不可用
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
        		admin, //用户
        		admin.getPassword(), //密码
                ByteSource.Util.bytes(username),
                getName()  //realm name
        );
        return authenticationInfo;
    }
    
    public static ShiroCompanyRealm getInstance() {
    	DefaultSecurityManager securityManager = (DefaultSecurityManager) SecurityUtils.getSecurityManager();
    	Collection<Realm> realms = securityManager.getRealms();
    	Collection<Realm> typeRealms = new ArrayList<>();
        for (Realm realm : realms) {
            if (realm.getName().contains(LoginType.COMPANY.toString()))
                typeRealms.add(realm);
        }
        ShiroCompanyRealm realm = (ShiroCompanyRealm) typeRealms.iterator().next();
		return realm;
    }
    
    //获取用户加密密码
    public static String encrypt(Object source, ByteSource salt) {
    	HashedCredentialsMatcher credentialsMatcher = (HashedCredentialsMatcher) ShiroCompanyRealm.getInstance().getCredentialsMatcher();
    	String hashAlgorithm = credentialsMatcher.getHashAlgorithmName();
    	int iterations = credentialsMatcher.getHashIterations();
    	SimpleHash hash = new SimpleHash(hashAlgorithm, source, salt, iterations);
    	return hash.toString();
    }
    
    /**
     * 根据userId 清除当前session存在的用户的权限缓存
     * @param userIds 已经修改了权限的userId
     */
    public void clearUserAuthByUserId(List<Integer> userIds){
        if(null == userIds || userIds.size() == 0)	return ;
        //获取所有session
        Collection<Session> sessions = redisSessionDAO.getActiveSessions();
        //定义返回
        List<SimplePrincipalCollection> list = new ArrayList<SimplePrincipalCollection>();
        for (Session session:sessions){
            //获取session登录信息。
            Object obj = session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
            if(null != obj && obj instanceof SimplePrincipalCollection){
                //强转
                SimplePrincipalCollection spc = (SimplePrincipalCollection)obj;
                //判断用户，匹配用户ID。
                obj = spc.getPrimaryPrincipal();
                if(null != obj && obj instanceof Admin){
                    Admin admin = (Admin) obj;
                    //比较用户ID，符合即加入集合
                    if(null != admin && userIds.contains(admin.getUuid())){
                        list.add(spc);
                    }
                }
            }
        }
        RealmSecurityManager securityManager =
                (RealmSecurityManager) SecurityUtils.getSecurityManager();
        ShiroCompanyRealm realm = (ShiroCompanyRealm)securityManager.getRealms().iterator().next();
        for (SimplePrincipalCollection simplePrincipalCollection : list) {
            realm.clearCachedAuthorizationInfo(simplePrincipalCollection);
        }
    }
}
