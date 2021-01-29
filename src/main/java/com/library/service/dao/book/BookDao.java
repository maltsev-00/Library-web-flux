package com.library.service.dao.book;

import com.library.model.Book;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

public interface BookDao {

    Mono<Book> updateBook(Book book,Boolean available);
    Mono<Book> findBookById(UUID id);
    Mono<Book> findBookByIdAndAvailableTrue(UUID id);

}
