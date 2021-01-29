package com.library.controller;

import com.library.config.DataBaseConfig;
import com.library.config.MongoDbContainer;
import com.library.model.Book;
import com.library.model.Reservation;
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
@Slf4j
@ContextConfiguration(initializers = ReservationControllerTest.class)
class ReservationControllerTest  implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private Book book;

    @Autowired
    private DataBaseConfig dataBaseConfig;


    private static MongoDbContainer mongoDbContainer;

    @BeforeAll
    public static void startContainerAndPublicPortIsAvailable() {
        mongoDbContainer = new MongoDbContainer();
        mongoDbContainer.start();
    }



    @Test
    @WithUserDetails("userTest")
    void addReservation() {
        webTestClient.post()
                .uri("/reservation/addReservation")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(Collections.singletonList(book)), List.class)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .consumeWith(response ->
                        Assertions.assertThat(response.getResponseBody()).isNotEmpty());
    }

    @WithUserDetails("adminTest")
    @Test
    void updateDateReservation() {
        webTestClient.put()
                .uri("/reservation/renewal")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(new Reservation()), Reservation.class)
                .exchange()
                .expectStatus()
                .isBadRequest()
                .expectBody()
                .consumeWith(response ->
                        Assertions.assertThat(response.getResponseBody()).isNotEmpty());
    }

    @WithUserDetails("managerTest")
    @Test
    void showAllReservation() {
        webTestClient.get()
                .uri("/reservation/allReservation")
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