package cn.product.treasuremall.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cn.product.treasuremall.interceptor.IllegalCharacterFilter;

@Configuration
public class FilterConfig {
	
	@Bean
    public FilterRegistrationBean<IllegalCharacterFilter> registFilter() {
        FilterRegistrationBean<IllegalCharacterFilter> registration = new FilterRegistrationBean<IllegalCharacterFilter>();
        registration.setFilter(new IllegalCharacterFilter());
        registration.addUrlPatterns("/*");
        registration.setName("IllegalCharacterFilter");
        registration.setOrder(1);
        return registration;
    }
}
