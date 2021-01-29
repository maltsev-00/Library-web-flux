package com.library.service.books;

import com.library.config.MongoDbContainer;
import com.library.model.Book;
import com.library.repository.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.utility.DockerImageName;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = BookServiceImplTest.class)
@Slf4j
class BookServiceImplTest implements ApplicationContextInitializer<ConfigurableApplicationContext> {


    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private Book book;

    private static MongoDbContainer mongoDbContainer;

    @BeforeAll
    public static void startContainerAndPublicPortIsAvailable() {
        mongoDbContainer = new MongoDbContainer();
        mongoDbContainer.start();
    }


    @Test
    void addNewBook() {
        Flux<Book> books = bookRepository.findAll();
        StepVerifier.withVirtualTime(() -> books)
                .expectSubscription()
                .thenRequest(Long.MAX_VALUE)
                .expectNextCount(15)
                .expectComplete();
    }

    @Test
    void findAllBooks() {

     //   Mockito.when(bookRepository.findAll()).thenReturn(null);
    }

    @Test
    public void saveBook(){
        StepVerifier.create(Mono.just(book))
                .expectNext(book)
                .verifyComplete();


    }

    @Test
    public void testFindBookByGenre()
    {
        StepVerifier.create(Mono.just(book.getName()))
                .expectNext("123Космонавты")
                .verifyComplete();

    }

    @Test
    public void testDeleteBook() {
        StepVerifier.create(Mono.just(book.getId()))
                .expectNext(UUID.fromString("c2516fff-4b4f-4e0c-9a44-345152214c3b"))
                .verifyComplete();
    }

    @Test
    public void update() {
        Mockito
                .when(this.bookRepository.findById(book.getId()))
                .thenReturn(Mono.just(book));

        Mockito
                .when(this.bookRepository.save(book))
                .thenReturn(Mono.just(book));
    }

    @Override
    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
        log.info("Overrding Spring Properties for mongodb !!!!!!!!!");

        TestPropertyValues values = TestPropertyValues.of(
                "spring.data.mongodb.host=" + mongoDbContainer.getContainerIpAddress(),
                "spring.data.mongodb.port=" + mongoDbContainer.getPort()

        );
        values.applyTo(configurableApplicationContext);
    }


}