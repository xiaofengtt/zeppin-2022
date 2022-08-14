package cn.product.payment.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import cn.product.payment.interceptor.ApiCheckParamInterceptor;
import cn.product.payment.interceptor.CheckParamInterceptor;
import cn.product.payment.interceptor.CrosInterceptor;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer{
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		CrosInterceptor crosFilter = new CrosInterceptor();
		CheckParamInterceptor checkParamInterceptor = new CheckParamInterceptor();
		ApiCheckParamInterceptor apiCheckParamInterceptor = new ApiCheckParamInterceptor();
		
		registry.addInterceptor(crosFilter).addPathPatterns("/api/**");
		registry.addInterceptor(apiCheckParamInterceptor).addPathPatterns("/api/**");
		registry.addInterceptor(checkParamInterceptor).addPathPatterns("/system/**");
		registry.addInterceptor(checkParamInterceptor).addPathPatterns("/systemLogin/**");
	}

}
