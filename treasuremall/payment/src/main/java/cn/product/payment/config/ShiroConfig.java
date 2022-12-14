package cn.product.payment.config;

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

import cn.product.payment.service.MethodService;
import cn.product.payment.shiro.CompanyAdminAuthFilter;
import cn.product.payment.shiro.PermsAuthorizationFilter;
import cn.product.payment.shiro.ShiroSystemFilter;
import cn.product.payment.shiro.ShiroSystemRealm;
import cn.product.payment.shiro.ShiroCompanyRealm;
import cn.product.payment.shiro.ShiroRealmAuthenticator;

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
        ShiroFilterFactoryBean shiroFilterFactoryBean  = new ShiroFilterFactoryBean();
        
        // ???????????? SecurityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        
        Map<String, Filter> filters = new HashMap<String, Filter>();
        filters.put("systemFilter", systemFilter());
        filters.put("companyFilter", companyFilter());
        filters.put("perms", permissionFilter());
        shiroFilterFactoryBean.setFilters(filters);
        
        //?????????
        shiroFilterFactoryBean.setFilterChainDefinitionMap(methodService.loadFilterChainDefinitions());
        return shiroFilterFactoryBean;
    }
    
    @Bean
    public FormAuthenticationFilter systemFilter(){
    	ShiroSystemFilter systemFilter = new ShiroSystemFilter();
        return systemFilter;
    }

    @Bean
    public AccessControlFilter companyFilter(){
    	CompanyAdminAuthFilter companyFilter = new CompanyAdminAuthFilter();
        return companyFilter;
    }
    
    @Bean
    public PermissionsAuthorizationFilter permissionFilter(){
    	PermsAuthorizationFilter companyFilter = new PermsAuthorizationFilter();
        return companyFilter;
    }
    
    @Bean
    public SecurityManager securityManager(){
        DefaultWebSecurityManager securityManager =  new DefaultWebSecurityManager();
        securityManager.setAuthenticator(shiroRealmAuthenticator());
        //??????realm.
        Collection<Realm> realms = new ArrayList<Realm>();
        realms.add(shiroSystemRealm());
        realms.add(shiroCompanyRealm());
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
    public ShiroSystemRealm shiroSystemRealm(){
        ShiroSystemRealm shiroSystemRealm = new ShiroSystemRealm();
        shiroSystemRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return shiroSystemRealm;
    }
    
    @Bean
    public ShiroCompanyRealm shiroCompanyRealm(){
        ShiroCompanyRealm shiroCompanyRealm = new ShiroCompanyRealm();
        shiroCompanyRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return shiroCompanyRealm;
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
