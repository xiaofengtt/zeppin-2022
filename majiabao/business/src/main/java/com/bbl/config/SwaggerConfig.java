package com.bbl.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig extends WebMvcConfigurationSupport {



    /**
     * 创建该API的基本信息（这些基本信息会展现在文档页面中）
     * 访问地址：http://项目实际地址/swagger-ui.html
     * @return
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder ()
                .title ("版本信息后台API")
//                .description("欢迎使用")
                .termsOfServiceUrl ("")
                .contact ("code")
                .version ("1.0")
                .build ();
    }

    /**
     * 创建API应用
     * apiInfo() 增加API相关信息
     * 通过select()函数返回一个ApiSelectorBuilder实例,用来控制哪些接口暴露给Swagger来展现，
     * 本例采用指定扫描的包路径来定义指定要建立API的目录。
     * @return
     */
    @Bean
    public Docket createRestApi() {
        return new Docket (DocumentationType.SWAGGER_2)
                .groupName("支付")
                .apiInfo (apiInfo ())
                .select ()
                .apis (RequestHandlerSelectors.basePackage("com.bbl.pay"))
                .paths (PathSelectors.any ())
                .build ();

    }


    @Bean
    public Docket createRestApi2() {
        return new Docket (DocumentationType.SWAGGER_2)
                .groupName("闪电期指")
                .apiInfo (apiInfo ())
                .select ()
                .apis (RequestHandlerSelectors.basePackage("com.bbl.business"))
                .paths (PathSelectors.any ())
                .build ();
    }


    @Bean
    public Docket createRestApi3() {
        return new Docket (DocumentationType.SWAGGER_2)
                .groupName("趣拍卖")
                .apiInfo (apiInfo ())
                .select ()
                .apis (RequestHandlerSelectors.basePackage("com.bbl.auction"))
                .paths (PathSelectors.any ())
                .build ();
    }


}