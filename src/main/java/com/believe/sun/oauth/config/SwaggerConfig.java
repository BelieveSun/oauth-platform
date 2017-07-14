package com.believe.sun.oauth.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

/**
 * Created by sungj on 17-6-6.
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Value("${swagger.enable}")
    private Boolean enable;

    @Bean
    public Docket createRestApi() {
        ArrayList<SecurityScheme> list = new ArrayList<>();
        list.add(apiKey());
        return new Docket(DocumentationType.SWAGGER_2)
                .securitySchemes(list)
                .apiInfo(apiInfo())
                .enable(enable)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.believe.sun"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiKey apiKey() {
        return new ApiKey("Authorization", "OAuth2 Token", "header");
    }


    private ApiInfo apiInfo() {
        Contact contact = new Contact("sungj","","15680028136@163.com");
        return new ApiInfoBuilder()
                .title("OAuth2 管理")
                .description("OAuth2 API")
                .contact(contact)
                .version("1.0")
                .build();
    }
}
