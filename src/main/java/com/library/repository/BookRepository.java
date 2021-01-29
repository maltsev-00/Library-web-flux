package com.library.repository;

import com.library.model.Author;
import com.library.model.Book;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@Repository
@Primary
public interface BookRepository extends ReactiveMongoRepository<Book, UUID>  {

    Flux<Book> findBooksByGenre(List<String> genres);
    Flux<Book> findListBooksByAuthors(@Param("name") List<Author> authors);
    Flux<Book> findBooksByTranslators(List<String> translators);
    Mono<Book> findById(UUID id);
    Mono<Book> findBookByNameOrPublisherOrAuthorsOrTranslatorsOrYear(String name,String publisher,@Param("name") List<Author> authors,List<String> translators,String year);
    Mono<Book> findBookByIdAndAvailableTrue(UUID id);
}
