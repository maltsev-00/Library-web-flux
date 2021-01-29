package com.library.config;

import com.library.enums.UserRole;
import com.library.model.Author;
import com.library.model.Book;
import com.library.model.Reservation;
import com.library.model.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;

@Configuration
public class Entity {

    @Bean
    public Book testBook(){
        return new Book(UUID.fromString("c2516fff-4b4f-4e0c-9a44-345152214c3b"),
                "123Космонавты",
                Collections.singletonList(new Author("Роза")),
                "pu123blisher 2",
                "2021230",
                Arrays.asList("Сивилия","Олли"),
                "Книга о к123123осмосе",
                Collections.singletonList("Фант1астика"),
                true);
    }

    @Bean("user")
    public User testUser(){
        return new User(UUID.randomUUID(),
                "userTest",
                "passwordTest",
                UserRole.ROLE_USER);
    }

    @Bean("admin")
    public User testAdmin(){

        return new User(UUID.randomUUID(),
                "adminTest",
                "passwordTest",
                UserRole.ROLE_ADMIN);
    }

    @Bean("manager")
    public User testManager(){
        return new User(UUID.randomUUID(),
                "managerTest",
                "passwordTest",
                UserRole.ROLE_MANAGER);
    }

    @Bean
    public Reservation testReservation(){
        return  new Reservation(UUID.randomUUID(),
                "TestUser",testBook(),
                LocalDate.now());
    }

}
