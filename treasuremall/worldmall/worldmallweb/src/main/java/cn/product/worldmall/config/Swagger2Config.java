package cn.product.worldmall.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
//@EnableWebMvc
public class Swagger2Config {
	private final static Logger log = LoggerFactory.getLogger(Swagger2Config.class);

	@Value("${sop.swagger.enable}")
	private boolean enable;
	@Value("${sop.swagger.package.scan}")
	private String packageScan;
	@Value("${sop.swagger.title}")
	private String title;
	@Value("${sop.swagger.description}")
	private String description;
	@Value("${sop.swagger.version}")
	private String version;

	/**
	 * swagger2的配置文件，这里可以配置swagger2的一些基本的内容，比如扫描的包等等
	 * 
	 * @return
	 */
	@Bean
	public Docket createRestfulApi() {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())
				.tags(new Tag("login", "用户登录"), getTags())
				.select()
				.apis(RequestHandlerSelectors.basePackage(packageScan)) // 暴露接口地址的包路径
//				.apis(RequestHandlerSelectors.withClassAnnotation(ActionParam.class))
				.paths(PathSelectors.any()).build();
	}

	/**
	 * 构建 api文档的详细信息函数,注意这里的注解引用的是哪个
	 * 
	 * @return
	 */
	private ApiInfo apiInfo() {
//		Contact contact = new Contact("LanveToBigData", "http://www.cnblogs.com/zhangyinhua/", "917484312@qq.com");
		return new ApiInfoBuilder()
				// 页面标题
				.title(title)
				// 创建人
//				.contact(contact)
				// 版本号
				.version(version)
				// 描述
				.description(description).build();
	}
	
	private Tag[] getTags() {
        Tag[] tags = {
//            new Tag("login", "用户登录"),
            new Tag("area", "前端地区"),
            new Tag("bank", "银行信息"),
            new Tag("recommend", "邀新活动"),
            new Tag("sms", "短信通道"),
            new Tag("userAccount", "用户账户管理"),
            new Tag("userAddress", "用户地址管理"),
            new Tag("card", "用户支付账号绑定管理"),
            new Tag("user", "用户个人信息管理"),
            new Tag("goods", "商品管理"),
            new Tag("comment", "拍拍圈管理"),
            new Tag("userRecharge", "用户充值管理"),
            new Tag("userWithdraw", "用户提现管理"),
            new Tag("capital", "支付渠道管理"),
            new Tag("payment", "用户下单管理"),
            new Tag("ranklist", "排行榜"),
            new Tag("banner", "广告图管理"),
            new Tag("message", "用户站内消息"),
            new Tag("activity", "活动管理"),
            new Tag("userActivity", "用户活动数据")
        };
        return tags;
    }
}
