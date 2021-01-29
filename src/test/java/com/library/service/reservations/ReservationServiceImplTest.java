package com.library.service.reservations;

import com.library.config.MongoDbContainer;
import com.library.model.Book;
import com.library.model.Reservation;
import com.library.service.books.BookService;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.internal.engine.valueextraction.ArrayElement;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = ReservationServiceImplTest.class)
@Slf4j
class ReservationServiceImplTest implements ApplicationContextInitializer<ConfigurableApplicationContext> {


    @Autowired
    private ReservationService reservationService;

    @Autowired
    private Book book;

    @Autowired
    private Reservation reservation;


    private static MongoDbContainer mongoDbContainer;

    @BeforeAll
    public static void startContainerAndPublicPortIsAvailable() {
        mongoDbContainer = new MongoDbContainer();
        mongoDbContainer.start();
    }


    @Test
    void deleteReservation() {
        StepVerifier.create(reservationService.deleteReservation(reservation))
                .expectNext(reservation)
                .verifyComplete();

    }


    @Test
    void showAllReservation() {

        assertThatExceptionOfType(AssertionError.class)
                .isThrownBy(() -> StepVerifier.create(reservationService.showAllReservation())
                        .consumeNextWith(s -> {
                            log.info(s.getBook().getName());
                            log.info(reservation.getBook().getName());
                            if (!reservation.getBook().getName().equals(s.getBook().getName())) {
                                throw new AssertionError(s);
                            }
                        })
                        .expectComplete()
                        .verify())
                .withMessage("Error");
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