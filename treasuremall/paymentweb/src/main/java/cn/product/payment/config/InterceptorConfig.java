package cn.product.payment.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import cn.product.payment.interceptor.CheckParamInterceptor;
import cn.product.payment.interceptor.CrosInterceptor;

/**
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer{
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		CrosInterceptor crosFilter = new CrosInterceptor();
		CheckParamInterceptor checkParamInterceptor = new CheckParamInterceptor();
		
//		registry.addInterceptor(crosFilter).addPathPatterns("/swagger-ui.html/**");
		registry.addInterceptor(crosFilter).addPathPatterns("/front/**");
		registry.addInterceptor(crosFilter).addPathPatterns("/loginFront/**");
		registry.addInterceptor(checkParamInterceptor).addPathPatterns("/**");
		
	}

}
