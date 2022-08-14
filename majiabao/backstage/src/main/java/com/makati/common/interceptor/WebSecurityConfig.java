package com.makati.common.interceptor;

import com.makati.common.convert.DateConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebSecurityConfig extends WebMvcConfigurerAdapter {

//
//    @Bean
//    public TokenInterceptor getTokenInterceptor() {
//        return new TokenInterceptor();
//    }
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(getTokenInterceptor()).excludePathPatterns("/","/login","/user/login","/static/**","/register","/user/add","/user/checkUser","/webjars");
//        super.addInterceptors(registry);
//
//    }
//
//    @Override
//    public void addFormatters(FormatterRegistry registry) {
//        registry.addConverter(new DateConverter());
//    }
}