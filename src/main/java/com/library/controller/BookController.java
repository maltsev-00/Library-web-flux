package com.library.controller;

import com.library.model.Author;
import com.library.model.Book;
import com.library.model.dto.BookDto;
import com.library.service.books.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@RestController
@RequestMapping("books")
@Slf4j
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("allBooks")
    public Flux<Book> showAllBooks() {
        log.info("Show all books ");
        return bookService.findAllBooks();
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("booksFree")
    public Flux<BookDto> showFreeBooks() {
        log.info("Show all free books ");
        return bookService.freeBooks();
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("searchBooksByAuthor")
    public Flux<Book> showBooksByAuthor(@RequestBody List<@Valid Author> nameAuthor) {
        log.info("Authors : "+nameAuthor.toString());
        return bookService.searchBooksByAuthor(nameAuthor);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("searchBooksByGenre")
    public Flux<Book> showBooksByGenre(@RequestBody List<@NotEmpty String> nameGenres) {
        log.info("Genres : "+nameGenres.toString());
        return bookService.searchBooksByGenre(nameGenres);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("searchBookByTranslate")
    public Flux<Book> searchByBooksByTranslator(@RequestBody List<@NotEmpty String> translators) {
        log.info("Translators : "+translators.toString());
        return bookService.searchBookTranslator(translators);
    }
}

