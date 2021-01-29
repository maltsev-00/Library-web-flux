package com.library.config;


import com.fasterxml.classmate.TypeResolver;
import com.library.model.dto.ReservationDto;
import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
@Data
public class SwaggerConfig {

    private final TypeResolver typeResolver;

    @Bean
    public Docket docket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.library.controller"))
                .paths(PathSelectors.any())
                .build()
                .additionalModels(typeResolver.resolve(ReservationDto.class));


    }

}