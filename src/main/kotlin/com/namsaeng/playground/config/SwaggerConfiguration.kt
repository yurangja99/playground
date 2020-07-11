package com.namsaeng.playground.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

@Configuration
@EnableSwagger2
class SwaggerConfiguration {
    @Bean
    fun swaggerApi(): Docket {
        return Docket(DocumentationType.SWAGGER_2)
                .apiInfo(swaggerInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.namsaeng.playground.controllers"))
                .paths(PathSelectors.any())
                .build()
                .useDefaultResponseMessages(false)
    }

    private fun swaggerInfo(): ApiInfo {
        return ApiInfoBuilder().title("API Document")
                .description("웹 서비스 개발 시 사용되는 서버 API에 대한 연동 문서")
                .license("namsaeng of Batoners")
                .licenseUrl("http://m.blog.naver.com/BlogTagView.nhn?orderType=date&tagName=%EB%B0%94%ED%86%A0%EB%84%88%EC%8A%A4")
                .version("1")
                .build()
    }
}