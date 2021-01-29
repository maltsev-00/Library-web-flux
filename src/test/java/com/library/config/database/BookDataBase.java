package com.library.config.database;

import com.library.model.Book;
import com.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;


public class BookDataBase implements DatabaseInit {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private Book book;

    @PostConstruct
    @Override
    public void initialize() {
     bookRepository.save(book).subscribe();
    }
}
