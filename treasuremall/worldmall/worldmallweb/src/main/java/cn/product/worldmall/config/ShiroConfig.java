package cn.product.worldmall.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cn.product.worldmall.api.base.DataResult;
import cn.product.worldmall.api.base.InputParams;
import cn.product.worldmall.control.TransferService;
import cn.product.worldmall.shiro.FrontUserAuthCheckFilter;
import cn.product.worldmall.shiro.PermsAuthorizationFilter;
import cn.product.worldmall.shiro.ShiroBackFilter;
import cn.product.worldmall.shiro.ShiroBackRealm;
import cn.product.worldmall.shiro.ShiroRealmAuthenticator;
import cn.product.worldmall.shiro.WebsocketCheckFilter;

/**
 */
@Configuration
public class ShiroConfig {
	
    @Autowired
    private TransferService transferService;

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
    @SuppressWarnings("unchecked")
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
        filters.put("socketFilter", socketFilter());
        shiroFilterFactoryBean.setFilters(filters);
        
        InputParams params = new InputParams("methodService", "loadFilterChainDefinitions");
        DataResult<Object> result = (DataResult<Object>) this.transferService.execute(params);
        if(result.getData() != null) {
        	System.out.println("--------------"+result.getData());
        	LinkedHashMap<String, String> filterChainDefinitions = (LinkedHashMap<String, String>) result.getData();
        	filterChainDefinitions.put("/link/**", "socketFilter");//websocket?????????????????????
        	filterChainDefinitions.put("/weblink/**", "socketFilter");//websocket?????????????????????
        	//?????????
            shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitions);
        } else {
        	LoggerFactory.getLogger(getClass()).error("?????????????????????????????????shiro???????????????");
        }
        
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
    public AccessControlFilter socketFilter(){
//    	ShiroFrontFilter frontFilter = new ShiroFrontFilter();
    	WebsocketCheckFilter socketFilter = new WebsocketCheckFilter();
        return socketFilter;
    }
    
    @Bean
    public SecurityManager securityManager(){
        DefaultWebSecurityManager securityManager =  new DefaultWebSecurityManager();
        securityManager.setAuthenticator(shiroRealmAuthenticator());
        //??????realm.
        Collection<Realm> realms = new ArrayList<Realm>();
        realms.add(shiroBackRealm());
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
        redisManager.setExpire(3600*4);// ????????????????????????
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
        sessionManager.setSessionIdUrlRewritingEnabled(false);//??????shiro?????????url??????JSESSIONID
        sessionManager.setGlobalSessionTimeout(3600000L*4);
        return sessionManager;
    }

}
