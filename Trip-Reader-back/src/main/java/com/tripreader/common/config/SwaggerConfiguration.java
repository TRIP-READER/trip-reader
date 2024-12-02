package com.tripreader.common.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

//Swagger-UI 확인
//http://localhost/tripreader/swagger-ui/index.html

@Configuration
public class SwaggerConfiguration {

	@Bean
	public OpenAPI openAPI() {
		Info info = new Info().title("TRIP READER API 명세서").description(
				"<h3>TRIP READER API</h3>광주 04 박하민, 허현준")
				.version("v1").contact(new io.swagger.v3.oas.models.info.Contact().name("박하민,허현준"));

		return new OpenAPI().components(new Components()).info(info);
	}
	
	@Bean
	public GroupedOpenApi allApi() {
		return GroupedOpenApi.builder().group("all").pathsToMatch("/**").build();
	}
//
//	@Bean
//	public GroupedOpenApi adminApi() {
//		return GroupedOpenApi.builder().group("ssafy-admin").pathsToMatch("/admin/**").build();
//	}
//
//	@Bean
//	public GroupedOpenApi userApi() {
//		return GroupedOpenApi.builder().group("ssafy-user").pathsToMatch("/user/**").build();
//	}
//	
//	@Bean
//	public GroupedOpenApi fileApi() {
//		return GroupedOpenApi.builder().group("ssafy-file").pathsToMatch("/file/**").build();
//	}

}
