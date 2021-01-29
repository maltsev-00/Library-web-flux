package com.library.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;


@Configuration
public class ValidatorConfig {

    @Bean
    public ValidatorFactory validatorFactory(){
        return  Validation.buildDefaultValidatorFactory();
    }

    @Bean
    public Validator validator(){
        return  validatorFactory().getValidator();
    }

}
