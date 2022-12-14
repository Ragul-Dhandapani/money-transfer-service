package com.org.moneytransfers.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    private ApiInfo apiInfo() {

        return new ApiInfoBuilder().version("1.0")
                .title("money-transfer-service")
                .description("Transfer Money between Two accounts")
                .contact(new Contact("Ragul Dhandapani" , "" , "abcd@gmail.com"))
                .build();
    }

    @Bean
    public Docket productInfo() {
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(true)
                .select().apis(RequestHandlerSelectors.basePackage("com.org.moneytransfers.controller"))
                .paths(PathSelectors.any())
                .build().apiInfo(apiInfo());
    }
}
