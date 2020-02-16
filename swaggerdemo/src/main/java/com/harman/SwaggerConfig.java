package com.harman;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
//Configuration file for swagger
public class SwaggerConfig {
    
	//this is mandatory bean creation wherein we actually configure swagger
	@Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.harman.controller"))	//controller package name
                .paths(regex("/product.*"))	// path which you want to exclude or include in api doc
                .build()
                .apiInfo(metaData());
    }
    
    //this is optinal method
    private ApiInfo metaData() {
        return new ApiInfo(
                "Spring Boot REST API",		//title
                "Spring Boot REST API for Online Store",		//description
                "1.0",	//version
                "http://google.co.in",		//termsOfServiceUrl
                new Contact("Pallavi Nawale", "https://google.co.in", "pallavi.jagtap@harman.com"),	//developer's contact
               "Apache License Version 2.0",	//License
                "https://www.apache.org/licenses/LICENSE-2.0");	//LicenseURL
    }
}