package cn.product.payment.config;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;

@Configuration
public class KaptchaConfig {

	@Bean(name = "captchaProducer")
	public DefaultKaptcha getKaptchaBean() {
		DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
		Properties properties = new Properties();
		// 图片边框
		properties.setProperty("kaptcha.border", "no");
//		// 边框颜色
//		properties.setProperty("kaptcha.border.color", "105,179,90");
		// 字体颜色
		properties.setProperty("kaptcha.textproducer.font.color", "39,1,247");
		// 图片宽
		properties.setProperty("kaptcha.image.width", "125");
		// 图片高
		properties.setProperty("kaptcha.image.height", "45");
//		// 字体大小
		properties.setProperty("kaptcha.textproducer.font.size", "30");
		// session key
		properties.setProperty("kaptcha.session.key", Constants.KAPTCHA_SESSION_KEY);
		// 验证码长度
		properties.setProperty("kaptcha.textproducer.char.length", "4");
		// 字体
		properties.setProperty("kaptcha.textproducer.font.names", "幼圆");
		// 文本集合，验证码值从此集合中获取
		properties.setProperty("kaptcha.textproducer.char.string", "0123456789");
		//文字间隔
		properties.setProperty("kaptcha.textproducer.char.space", "2");
		//背景颜色
		properties.setProperty("kaptcha.background.clear.from", "225,225,225");
	    properties.setProperty("kaptcha.background.clear.to", "225,225,225");
//		//去除干扰
//		properties.setProperty("kaptcha.noise.impl", "com.google.code.kaptcha.impl.NoNoise");
		//图片样式
		properties.setProperty("kaptcha.obscurificator.impl", "com.google.code.kaptcha.impl.ShadowGimpy");
		Config config = new Config(properties);
		defaultKaptcha.setConfig(config);
		return defaultKaptcha;
	}
}
