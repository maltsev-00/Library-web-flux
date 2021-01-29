package com.library.controller;

import com.library.config.MongoDbContainer;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = BookControllerTest.class)
@Slf4j
class BookControllerTest implements ApplicationContextInitializer<ConfigurableApplicationContext>{

    @Autowired
    private WebTestClient webTestClient;

    private static MongoDbContainer mongoDbContainer;


    @BeforeAll
    public static void startContainerAndPublicPortIsAvailable() {
        mongoDbContainer = new MongoDbContainer();
        mongoDbContainer.start();
    }



    @Test
    @WithUserDetails("adminTest")
    void showAllBooks() {
        webTestClient.get()
                .uri("/books/allBooks")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .consumeWith(response ->
                        Assertions.assertThat(response.getResponseBody()).isNotEmpty());
    }

    @Test
    @WithUserDetails("userTest")
    void showFreeBooks() {
        webTestClient.get()
                .uri("/books/booksFree")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .consumeWith(response ->
                        Assertions.assertThat(response.getResponseBody()).isNotEmpty());
    }

    @Test
    @WithUserDetails("userTest")
    void showBooksByAuthor() {
        webTestClient.post()
                .uri("/books/searchBooksByAuthor")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(Collections.singletonList("Роулинг")),List.class)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .consumeWith(response ->
                        Assertions.assertThat(response.getResponseBody()).isNotEmpty());

    }

    @Test
    @WithUserDetails("userTest")
    void showBooksByGenre() {
        webTestClient.post()
                .uri("/books/searchBooksByGenre")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(Collections.singletonList("Фантастика")),List.class)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .consumeWith(response ->
                        Assertions.assertThat(response.getResponseBody()).isNotEmpty());
    }

    @Test
    @WithUserDetails("userTest")
    void searchByBooksByTranslator() {
        webTestClient.post()
                .uri("/books/searchBooksByGenre")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(Collections.singletonList("Роулинг С.О.")),List.class)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .consumeWith(response ->
                        Assertions.assertThat(response.getResponseBody()).isNotEmpty());
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