package com.dazzlzy.common.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.ResponseBody;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger配置
 *
 * @author dazzlzy
 * @date 2018/5/22
 */
@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    @Value(value = "${swagger.enable}")
    private boolean swaggerEnable;

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                //.apis(RequestHandlerSelectors.withMethodAnnotation(ResponseBody.class))
                .apis(RequestHandlerSelectors.basePackage("com.dazzlzy.springbootseed.controller"))
                .paths(PathSelectors.any())
                .build()
                .enable(swaggerEnable)
                .pathMapping("/");
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("SpringBootSeed RESTful API 接口文档")
                .description("SpringBootSeed是一个SpringBoot的骨架项目，该项目集成SpringBoot2与众多常用框架，供学习使用")
                .version("0.1.0")
                .contact(new Contact("dazzlzy", "", ""))
                .build();
    }

}
