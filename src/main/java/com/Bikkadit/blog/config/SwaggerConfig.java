package com.Bikkadit.blog.config;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {


	public ApiKey apikeys() {
		return new ApiKey("JWT", Appconstants.AUTHORIZATION_HEADER, "Header");
	}
	

	private List<SecurityContext> securityContexts() {

		return Arrays.asList(SecurityContext.builder().securityReferences(Sr()).build());

	}
	private List<SecurityReference> Sr() {
		
		AuthorizationScope Scope = new AuthorizationScope("golbal", "Access EveryThing");
        return Arrays.asList(new SecurityReference("JWT", new AuthorizationScope[] { Scope }));

	}

	

	

	@Bean
	public Docket api() {

		return new Docket(DocumentationType.SWAGGER_2).apiInfo(getInfo()).securityContexts(securityContexts())
				.securitySchemes(Arrays.asList(apikeys())).select().apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any()).build();

	}

	private ApiInfo getInfo() {

		return new ApiInfo("Blogging Application:BackEnd Course", "This project Developed By Pallavi", "1.0",
				"Terms Of Service", new Contact("Pallavi", "http://pallavi.com", "pallavi@gmail.com"),
				"Licence of Apis", "Api license Url", Collections.emptyList());
	}
}
