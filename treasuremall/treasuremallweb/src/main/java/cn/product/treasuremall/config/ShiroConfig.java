package cn.product.treasuremall.config;

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

import cn.product.treasuremall.api.base.DataResult;
import cn.product.treasuremall.api.base.InputParams;
import cn.product.treasuremall.control.TransferService;
import cn.product.treasuremall.shiro.FrontUserAuthCheckFilter;
import cn.product.treasuremall.shiro.PermsAuthorizationFilter;
import cn.product.treasuremall.shiro.ShiroBackFilter;
import cn.product.treasuremall.shiro.ShiroBackRealm;
import cn.product.treasuremall.shiro.ShiroRealmAuthenticator;
import cn.product.treasuremall.shiro.WebsocketCheckFilter;

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
     * ShiroFilterFactoryBean 处理拦截资源文件问题。
     * 注意：单独一个ShiroFilterFactoryBean配置是或报错的，因为在
     * 初始化ShiroFilterFactoryBean的时候需要注入：SecurityManager
     *
     Filter Chain定义说明
     1、一个URL可以配置多个Filter，使用逗号分隔
     2、当设置多个过滤器时，全部验证通过，才视为通过
     3、部分过滤器可指定参数，如perms，roles
     *
     */
    @SuppressWarnings("unchecked")
	@Bean
    public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager){
        System.out.println("ShiroConfiguration.shirFilter()");
        ShiroFilterFactoryBean shiroFilterFactoryBean  = new ShiroFilterFactoryBean();
        
        // 必须设置 SecurityManager
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
        	filterChainDefinitions.put("/link/**", "socketFilter");//websocket安全连接过滤器
        	//拦截器
            shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitions);
        } else {
        	LoggerFactory.getLogger(getClass()).error("权限初始化失败，请检查shiro配置！！！");
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
        //设置realm.
        Collection<Realm> realms = new ArrayList<Realm>();
        realms.add(shiroBackRealm());
        securityManager.setRealms(realms);
        // 自定义缓存实现 使用redis
        securityManager.setCacheManager(cacheManager());
        // 自定义session管理 使用redis
        securityManager.setSessionManager(sessionManager());
        // 自定义Realm选择器
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
     * 凭证匹配器
     * （由于我们的密码校验交给Shiro的SimpleAuthenticationInfo进行处理了
     *  所以我们需要修改下doGetAuthenticationInfo中的代码;
     * ）
     * @return
     */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher(){
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();

        hashedCredentialsMatcher.setHashAlgorithmName("md5");//散列算法:这里使用MD5算法;
        hashedCredentialsMatcher.setHashIterations(2);//散列的次数，比如散列两次，相当于 md5(md5(""));

        return hashedCredentialsMatcher;
    }


    /**
     *  开启shiro aop注解支持.
     *  使用代理方式;所以需要开启代码支持;
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
     * 配置shiro redisManager
     * 使用的是shiro-redis开源插件
     * @return
     */
    public RedisManager redisManager() {
        RedisManager redisManager = new RedisManager();
        redisManager.setHost(host);
        redisManager.setPort(port);
        redisManager.setExpire(3600*4);// 配置缓存过期时间
        redisManager.setTimeout(timeout);
       // redisManager.setPassword(password);
        return redisManager;
    }

    /**
     * cacheManager 缓存 redis实现
     * 使用的是shiro-redis开源插件
     * @return
     */
    public RedisCacheManager cacheManager() {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());
        return redisCacheManager;
    }


    /**
     * RedisSessionDAO shiro sessionDao层的实现 通过redis
     * 使用的是shiro-redis开源插件
     */
    @Bean
    public RedisSessionDAO redisSessionDAO() {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(redisManager());
        return redisSessionDAO;
    }

    /**
     * shiro session的管理
     */
    @Bean
    public DefaultWebSessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionDAO(redisSessionDAO());
        sessionManager.setSessionIdUrlRewritingEnabled(false);//去掉shiro登录时url里的JSESSIONID
        sessionManager.setGlobalSessionTimeout(3600000L*4);
        return sessionManager;
    }

}
