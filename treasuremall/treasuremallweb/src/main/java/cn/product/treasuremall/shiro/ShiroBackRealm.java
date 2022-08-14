package cn.product.treasuremall.shiro;

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
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import cn.product.treasuremall.api.base.DataResult;
import cn.product.treasuremall.api.base.InputParams;
import cn.product.treasuremall.entity.Admin;
import cn.product.treasuremall.entity.Admin.AdminStatus;
import cn.product.treasuremall.control.TransferService;
import cn.product.treasuremall.entity.Method;

/**
 * 
 */
public class ShiroBackRealm extends AuthorizingRealm {

    @Autowired
    private TransferService transferService;

    @Autowired
    private RedisSessionDAO redisSessionDAO;

    //授权
    @SuppressWarnings("unchecked")
	@Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        Admin admin= (Admin) SecurityUtils.getSubject().getPrincipal();
        InputParams params = new InputParams("roleMethodService", "getMethodListByRole");
		params.addParams("role", null, admin.getRole());
        DataResult<Object> result = (DataResult<Object>) this.transferService.execute(params);
//        List<Method> methodList = methodService.getListByAdmin(admin.getUuid());
        List<Method> methodList = null;
        if(result.getData() != null) {
        	methodList = (List<Method>) result.getData();
        }
        // 权限信息对象info,用来存放查出的用户的所有的角色（role）及权限（permission）
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        if(methodList == null) {
        	LoggerFactory.getLogger(getClass()).error("授权错误，请检查相关信息！！！");
        }
        for(Method method: methodList){
            info.addStringPermission(method.getUuid());
        }
        return info;
    }

    //认证
    @SuppressWarnings("unchecked")
	@Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //获取用户的输入的账号.
    	UsernamePasswordToken upToken = (UsernamePasswordToken)token;  
        String username = (String)upToken.getPrincipal();
        
        InputParams params = new InputParams("adminService", "getByUsername");
		params.addParams("username", null, username);
        DataResult<Object> result = (DataResult<Object>) this.transferService.execute(params);
        Admin admin = null;
        if(result.getData() != null) {
        	admin = (Admin) result.getData();
        }
//        Admin admin = adminService.getByUsername(username);
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
    
    public static ShiroBackRealm getInstance() {
    	DefaultSecurityManager securityManager = (DefaultSecurityManager) SecurityUtils.getSecurityManager();
    	Collection<Realm> realms = securityManager.getRealms();
    	Collection<Realm> typeRealms = new ArrayList<>();
        for (Realm realm : realms) {
            if (realm.getName().contains(LoginType.BACK.toString()))
                typeRealms.add(realm);
        }
        ShiroBackRealm realm = (ShiroBackRealm) typeRealms.iterator().next();
		return realm;
    }
    
    //获取用户加密密码
    public static String encrypt(Object source, ByteSource salt) {
    	HashedCredentialsMatcher credentialsMatcher = (HashedCredentialsMatcher) ShiroBackRealm.getInstance().getCredentialsMatcher();
    	String hashAlgorithm = credentialsMatcher.getHashAlgorithmName();
    	int iterations = credentialsMatcher.getHashIterations();
    	SimpleHash hash = new SimpleHash(hashAlgorithm, source, salt, iterations);
    	return hash.toString();
    }
    
    /**
     * 根据adminId 清除当前session存在的用户的权限缓存
     * @param adminIds 已经修改了权限的adminId
     */
    public void clearAdminAuthById(List<String> adminIds){
        if(null == adminIds || adminIds.size() == 0)	return ;
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
                    if(null != admin && adminIds.contains(admin.getUuid())){
                        list.add(spc);
                    }
                }
            }
        }
        RealmSecurityManager securityManager =
                (RealmSecurityManager) SecurityUtils.getSecurityManager();
        ShiroBackRealm realm = (ShiroBackRealm)securityManager.getRealms().iterator().next();
        for (SimplePrincipalCollection simplePrincipalCollection : list) {
            realm.clearCachedAuthorizationInfo(simplePrincipalCollection);
        }
    }
}
