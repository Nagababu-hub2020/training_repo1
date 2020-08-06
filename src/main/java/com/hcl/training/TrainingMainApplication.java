package com.hcl.training;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@EnableSwagger2
@SpringBootApplication
public class TrainingMainApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(TrainingMainApplication.class, args);
	}

	@Bean
	public Docket applicationApi() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.hcl.training")).build()
				.apiInfo(metaInfo());
	}
	
	private ApiInfo metaInfo() {
		ApiInfo apiInfo = new ApiInfo("Swagger-REST Application ",
				" swagger with rest services ",
				"1.0 ",
				"terms of service ",
				new Contact("", "", ""),
				"",
				"",
				new ArrayList());
		return apiInfo;
	}
	
	private static final String[] AUTH_WHITELIST = {
                "**/swagger-resources/**",
                "/swagger-ui.html",
                "/v2/api-docs",
                "/webjars/**"
        };

        @Override
        public void configure(WebSecurity web) throws Exception {
               web.ignoring().antMatchers(AUTH_WHITELIST);
        }
}
