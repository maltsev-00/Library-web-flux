package com.library.service.books;

import com.library.config.MongoDbContainer;
import com.library.model.Book;
import com.library.repository.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
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
    private BookService bookService;

    @Autowired
    private Book book;

    private static MongoDbContainer mongoDbContainer;

    @BeforeAll
    public static void startContainerAndPublicPortIsAvailable() {
        mongoDbContainer = new MongoDbContainer();
        mongoDbContainer.start();
    }

    @Test
    void findAllBooks() {
        StepVerifier.create(bookService.findAllBooks())
                .expectNext(book)
                .verifyComplete();
    }

    @Test
    public void saveBook(){
        StepVerifier.create(bookRepository.save(book))
                .expectNext(book)
                .verifyComplete();
    }

    @Test
    public void testFindBookByGenre()
    {
        StepVerifier.create(bookService.searchBooksByGenre(book.getGenre()))
                .expectNext(book)
                .verifyComplete();

    }

    @Test
    public void findBookById() {
        StepVerifier.create(bookRepository.findById(book.getId()))
                .expectNext(book)
                .verifyComplete();
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