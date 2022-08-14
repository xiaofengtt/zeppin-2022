package com.bbl.config;

import com.bbl.cache.redis.JedisUtils;
import com.bbl.cache.redis.RedisMsgSubListener;
import com.bbl.cache.redis.RedisSubscribe;
import com.bbl.interceptor.TokenInterceptor;
import com.bbl.thread.RejectedExecutionHandlerBusiness;
import com.bbl.thread.ThreadFactorybusiness;
import com.bbl.thread.ThreadPoolExecutorbusiness;
import com.bbl.thread.Threadpool;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.concurrent.*;


@Configuration
public class BeanConfig {


    /**
     * redisTemplate 序列化使用的jdkSerializeable, 存储二进制字节码, 所以自定义序列化类
     *
     * @param redisConnectionFactory
     * @return
     */
    @Bean
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<> ();
        redisTemplate.setConnectionFactory (redisConnectionFactory);

        // 使用Jackson2JsonRedisSerialize 替换默认序列化
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer (Object.class);

        ObjectMapper objectMapper = new ObjectMapper ();
        objectMapper.setVisibility (PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping (ObjectMapper.DefaultTyping.NON_FINAL);

        jackson2JsonRedisSerializer.setObjectMapper (objectMapper);

        // 设置value的序列化规则和 key的序列化规则
        redisTemplate.setKeySerializer (new StringRedisSerializer ());
        redisTemplate.setValueSerializer (jackson2JsonRedisSerializer);
        redisTemplate.afterPropertiesSet ();
        return redisTemplate;
    }

//
//    /**
//     * 初始化线池
//     */
//    @Bean
//    public ExecutorService threadPool() {
//        Integer core=10;
//        Integer max=20;
//        Integer timeout=60;
//        LinkedBlockingQueue<Runnable> queue=new LinkedBlockingQueue<Runnable>();
//        ThreadFactorybusiness rfb=new ThreadFactorybusiness ();
//        RejectedExecutionHandlerBusiness rehb=new RejectedExecutionHandlerBusiness ();
//        ThreadPoolExecutorbusiness threadPool = new ThreadPoolExecutorbusiness(core,max,timeout,TimeUnit.SECONDS, queue,rfb,rehb);
//        for (int i = 1; i < core; i++) {
//            if(i>=core/2){
//                break;
//            }
//            threadPool.prestartAllCoreThreads();
//        }
//        return threadPool;
//    }

    /**
     * 拦截器
     */
    @Configuration
    public class WebConfig extends WebMvcConfigurationSupport {
        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
            registry.addResourceHandler("/static/**").addResourceLocations(
                    "classpath:/static/");
            registry.addResourceHandler("swagger-ui.html").addResourceLocations(
                    "classpath:/META-INF/resources/");
            registry.addResourceHandler("/webjars/**").addResourceLocations(
                    "classpath:/META-INF/resources/webjars/");
            super.addResourceHandlers(registry);
        }



        @Bean
        public TokenInterceptor getTokenInterceptor() {
            return new TokenInterceptor();
        }
        @Override
        public void addInterceptors(InterceptorRegistry registry) {
            registry.addInterceptor(getTokenInterceptor()).excludePathPatterns("/webjars/**","/swagger-ui.html","/swagger-resources/**","/static/**","/csrf**","/error/**");
            super.addInterceptors(registry);

        }


    }


    /**
     * 解决跨域
     */
    @Bean
    public CorsConfiguration corsConfiguration() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        //实际请求中允许携带的首部字段
        corsConfiguration.addAllowedHeader("*");
        //允许那些域跨域访问
        corsConfiguration.addAllowedOrigin("*");
        //允许那些请求方法
        corsConfiguration.addAllowedMethod("*");
        return corsConfiguration;
    }

    @Bean
    public CorsFilter corsFilter(CorsConfiguration corsConfiguration) {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration); // 4
        return new CorsFilter(source);
    }


}