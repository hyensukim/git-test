package com.springboot.securitytest.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
 * 1. Springdoc의 SwaggerConfig 설정
 *  Springfox와 설정 차이는 Springfox는 별도의 config 클래스에서 대부분의 설정을 진행하였지만, Springdoc는
 *  Springdoc는 config 클래스에서 API 문서페이지의 기본 명세 작성 및 OpenAPI 객체 빈등록만 해준다.
 *  나머지 설정은 application.yml 또는 application.yml 에서 설정한다.
 *  또한, @EnableWebMvc 어노테이션을 붙여주지도 않는다.
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI(){
        return new OpenAPI()
                .components(new Components())
                .info(apiInfo());
    }

    private Info apiInfo(){
        return new Info()
                .title("Springdoc 테스트")
                .description("Springdoc를 사용한 Swagger UI 테스트")
                .version("1.0.0");
    }

}