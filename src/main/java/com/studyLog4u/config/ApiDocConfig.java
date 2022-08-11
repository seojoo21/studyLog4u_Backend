package com.studyLog4u.config;

import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import lombok.Value;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiDocConfig {

    @Bean
    public OpenAPI openAPI(){
        Info info = new Info()
                .title("StudyLog4u API")
                .version("1.0")
                .description("StudyLog4U API 입니다.")
                .contact(new Contact()
                        .name("seojoo")
                        .email("kangj2016@gmail.com")
                        .url("https://seojoo21.tistory.com/"));

        return new OpenAPI().info(info);
    }
}
