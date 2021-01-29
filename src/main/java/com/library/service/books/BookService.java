package com.library.service.books;

import com.library.model.Author;
import com.library.model.Book;
import com.library.model.dto.BookDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface BookService {

    Mono<Object> addNewBook(Book book);
    Flux<Book> findAllBooks();
    Flux<BookDto> freeBooks();
    Flux<Book> searchBooksByAuthor(List<Author> namesAuthor);
    Flux<Book> searchBooksByGenre(List<String> namesGenre);
    Flux<Book> searchBookTranslator(List<String> translator);

}
