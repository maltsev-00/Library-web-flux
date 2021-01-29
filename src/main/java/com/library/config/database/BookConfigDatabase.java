package com.library.config.database;

import com.library.model.Author;
import com.library.model.Book;
import com.library.repository.BookRepository;
import com.library.service.valid.Validation;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;

@Data
@Slf4j
public class BookConfigDatabase implements DatabaseInitialize{

    private final Validation validator;
    private final BookRepository bookRepository;

    @PostConstruct
    @Override
    public void initialize() {
        Flux<Book> books = Flux.just(
                new Book(UUID.randomUUID(),"Цветы для Элджернона",
                        Collections.singletonList(new Author("Дэниел Киз")),
                        "publisher 1",
                        "2016",
                        Collections.singletonList("Даниэль"),
                        "Книга о цветах",
                        Collections.singletonList("Драма"),
                        true),

                new Book(UUID.randomUUID(),
                        "Космонавты",
                        Collections.singletonList(new Author("Роза")),
                        "publisher 2",
                        "2020",
                        Arrays.asList("Сивилия","Олли"),
                        "Книга о космосе",
                        Collections.singletonList("Фантастика"),
                        true),

                new Book(UUID.randomUUID(),
                        "Гарри Поттер",
                        Collections.singletonList(new Author("Роулинг")),
                        "publisher 3",
                        "2018",
                        Collections.singletonList("Роулинг С.О."),
                        "Фантастическая книга",
                        Arrays.asList("Фантастика","Драма"),
                        true));

            books.filter(book->validator.validNewOperand(book).equals(true))
                    .cast(Book.class)
                    .flatMap(bookRepository::save)
                    .subscribe(book -> log.info(book.getName()+" is added to database ..."));
    }
}
