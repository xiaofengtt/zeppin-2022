package org.linlinjava.litemall.wx.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * swagger在线文档配置<br>
 * 项目启动后可通过地址：http://host:ip/swagger-ui.html 查看在线文档
 *
 * @author enilu
 * @version 2018-07-24
 */

@Configuration
@EnableSwagger2
public class WxSwagger2Configuration {
    @Bean
    public Docket wxDocket() {

        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("wx")
                .apiInfo(wxApiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("org.linlinjava.litemall.wx.web"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo wxApiInfo() {
        return new ApiInfoBuilder()
                .title("litemall-wx API")
                .description("litemallApi..")
                .termsOfServiceUrl("")
                .contact("")
                .version("1.0")
                .build();
    }
    //所有接口需传签名校验如:sign=(所有入参,排除sign参数后。按自然数排列加上秘匙用md5,32位小写加密)
    //秘匙分2类: 1.默认秘匙:fb2356ddf5scc5d4d2s9e@2scwu7io2c
    //           2.user_token秘匙:调登入接口后json串里的md5Salt参数
}
