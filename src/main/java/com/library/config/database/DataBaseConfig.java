package com.library.config.database;


import com.library.repository.BookRepository;
import com.library.repository.UserRepository;
import com.library.service.valid.Validation;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@ConditionalOnProperty(name = "service.database.data", havingValue = "true")
@Data
@Slf4j
@RequiredArgsConstructor
public class DataBaseConfig {

    private final Validation validator;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Bean
    public DatabaseInitialize initializeBooks(){
        return new BookConfigDatabase(validator,bookRepository);
    }


    @Bean
    public DatabaseInitialize initializeUsers(){
        return new UserConfigDatabase(validator,userRepository,passwordEncoder);
    }

}
