package com.account.validation.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.account.validation.util.BankAccountConstants;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

	/**
	 * 
	 * @return
	 */
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).groupName("validation-api").host("").apiInfo(apiInfo()).select()
				.apis(RequestHandlerSelectors.basePackage(BankAccountConstants.BASE_PACKAGE)).paths(PathSelectors.any()).build();
	}

	/**
	 * 
	 * @return
	 */
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title(BankAccountConstants.BANK_ACCOUNT_TITLE).version("Build: 1.0").build();
	}

}