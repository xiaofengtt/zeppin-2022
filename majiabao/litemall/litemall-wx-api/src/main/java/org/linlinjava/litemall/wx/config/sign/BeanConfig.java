package org.linlinjava.litemall.wx.config.sign;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
@Configuration
public class BeanConfig {


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
            registry.addInterceptor(getTokenInterceptor()).excludePathPatterns("/swagger-ui.html","/swagger-resources/**","/webjars/**");
//            "/","/login","/user/login","/static/**","/register","/user/add","/user/checkUser","/webjars","/apponoff/**","/swagger-ui.html","/swagger-resources/**","/webjars/**","/getversion"
            super.addInterceptors(registry);

        }

    }


}

