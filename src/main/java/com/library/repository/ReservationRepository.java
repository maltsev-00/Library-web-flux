package com.library.repository;

import com.library.model.Book;
import com.library.model.Reservation;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.UUID;

@Repository
@Primary
public interface ReservationRepository extends ReactiveMongoRepository<Reservation, UUID> {

    Flux<Reservation> findReservationsByDateLessThan(LocalDate date);
    Mono<Void> deleteReservationByBookId(UUID id);

}
