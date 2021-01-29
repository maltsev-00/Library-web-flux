package com.library.config;

import com.library.model.Book;
import com.library.model.User;
import com.library.repository.BookRepository;
import com.library.repository.UserRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;

@Component
@Data
public class DataBaseConfig {

    @Autowired
    private Map<String, User> mapUser;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private Book book;


    @PostConstruct
    public void addUser(){
        userRepository.save(mapUser.get("user")).subscribe();
        userRepository.save(mapUser.get("admin")).subscribe();
        userRepository.save(mapUser.get("manager")).subscribe();
    }

    @PostConstruct
    public void addBook(){
        bookRepository.save(book).subscribe();
    }


}
