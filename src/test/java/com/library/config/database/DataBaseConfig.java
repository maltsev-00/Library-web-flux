package com.library.config.database;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataBaseConfig {

    @Bean
    public BookDataBase bookDataBase(){
        return new BookDataBase();
    }

    @Bean
    public UserDataBase userDataBase(){
        return new UserDataBase();
    }
}
