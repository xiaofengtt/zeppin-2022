package cn.zeppin.product.score.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cn.zeppin.product.score.service.MethodService;
import cn.zeppin.product.score.shiro.FrontUserAuthCheckFilter;
import cn.zeppin.product.score.shiro.PermsAuthorizationFilter;
import cn.zeppin.product.score.shiro.ShiroBackFilter;
import cn.zeppin.product.score.shiro.ShiroBackRealm;
import cn.zeppin.product.score.shiro.ShiroFrontRealm;
import cn.zeppin.product.score.shiro.ShiroRealmAuthenticator;

@Configuration
public class ShiroConfig {
	
    @Autowired
    private MethodService methodService;

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;

    @Value("${spring.redis.timeout}")
    private int timeout;

    @Value("${spring.redis.password}")
    private String password;

    @Bean
    public static LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }
    
    /**
     * ShiroFilterFactoryBean ?????????????????????????????????
     * ?????????????????????ShiroFilterFactoryBean?????????????????????????????????
     * ?????????ShiroFilterFactoryBean????????????????????????SecurityManager
     *
     Filter Chain????????????
     1?????????URL??????????????????Filter?????????????????????
     2?????????????????????????????????????????????????????????????????????
     3???????????????????????????????????????perms???roles
     *
     */
    @Bean
    public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager){
        System.out.println("ShiroConfiguration.shirFilter()");
        ShiroFilterFactoryBean shiroFilterFactoryBean  = new ShiroFilterFactoryBean();
        
        // ???????????? SecurityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        
        Map<String, Filter> filters = new HashMap<String, Filter>();
        filters.put("backFilter", backFilter());
        filters.put("frontFilter", frontFilter());
        filters.put("perms", permissionFilter());
        shiroFilterFactoryBean.setFilters(filters);
        
        //?????????
        shiroFilterFactoryBean.setFilterChainDefinitionMap(methodService.loadFilterChainDefinitions());
        return shiroFilterFactoryBean;
    }
    
    @Bean
    public FormAuthenticationFilter backFilter(){
    	ShiroBackFilter backFilter = new ShiroBackFilter();
        return backFilter;
    }

    @Bean
    public AccessControlFilter frontFilter(){
//    	ShiroFrontFilter frontFilter = new ShiroFrontFilter();
    	FrontUserAuthCheckFilter frontFilter = new FrontUserAuthCheckFilter();
        return frontFilter;
    }
    
    @Bean
    public PermissionsAuthorizationFilter permissionFilter(){
    	PermsAuthorizationFilter frontFilter = new PermsAuthorizationFilter();
        return frontFilter;
    }
    
    @Bean
    public SecurityManager securityManager(){
        DefaultWebSecurityManager securityManager =  new DefaultWebSecurityManager();
        securityManager.setAuthenticator(shiroRealmAuthenticator());
        //??????realm.
        Collection<Realm> realms = new ArrayList<Realm>();
        realms.add(shiroBackRealm());
        realms.add(shiroFrontRealm());
        securityManager.setRealms(realms);
        // ????????????????????? ??????redis
        securityManager.setCacheManager(cacheManager());
        // ?????????session?????? ??????redis
        securityManager.setSessionManager(sessionManager());
        // ?????????Realm?????????
        return securityManager;
    }
    
    @Bean
    public ShiroRealmAuthenticator shiroRealmAuthenticator(){
    	ShiroRealmAuthenticator shiroRealmAuthenticator = new ShiroRealmAuthenticator();
    	return shiroRealmAuthenticator;
    }
    
    @Bean
    public ShiroBackRealm shiroBackRealm(){
        ShiroBackRealm shiroBackRealm = new ShiroBackRealm();
        shiroBackRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return shiroBackRealm;
    }
    
    @Bean
    public ShiroFrontRealm shiroFrontRealm(){
        ShiroFrontRealm shiroFrontRealm = new ShiroFrontRealm();
        shiroFrontRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return shiroFrontRealm;
    }
    
    /**
     * ???????????????
     * ????????????????????????????????????Shiro???SimpleAuthenticationInfo???????????????
     *  ???????????????????????????doGetAuthenticationInfo????????????;
     * ???
     * @return
     */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher(){
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();

        hashedCredentialsMatcher.setHashAlgorithmName("md5");//????????????:????????????MD5??????;
        hashedCredentialsMatcher.setHashIterations(2);//???????????????????????????????????????????????? md5(md5(""));

        return hashedCredentialsMatcher;
    }


    /**
     *  ??????shiro aop????????????.
     *  ??????????????????;??????????????????????????????;
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager){
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    /**
     * ??????shiro redisManager
     * ????????????shiro-redis????????????
     * @return
     */
    public RedisManager redisManager() {
        RedisManager redisManager = new RedisManager();
        redisManager.setHost(host);
        redisManager.setPort(port);
        redisManager.setExpire(1800);// ????????????????????????
        redisManager.setTimeout(timeout);
       // redisManager.setPassword(password);
        return redisManager;
    }

    /**
     * cacheManager ?????? redis??????
     * ????????????shiro-redis????????????
     * @return
     */
    public RedisCacheManager cacheManager() {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());
        return redisCacheManager;
    }


    /**
     * RedisSessionDAO shiro sessionDao???????????? ??????redis
     * ????????????shiro-redis????????????
     */
    @Bean
    public RedisSessionDAO redisSessionDAO() {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(redisManager());
        return redisSessionDAO;
    }

    /**
     * shiro session?????????
     */
    @Bean
    public DefaultWebSessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionDAO(redisSessionDAO());
        return sessionManager;
    }

}
