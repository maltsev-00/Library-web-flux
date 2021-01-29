package com.library.service.reservations;

import com.library.model.Book;
import com.library.model.Reservation;
import com.library.model.dto.ReservationDto;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ReservationService {

    Flux<Reservation> addNewReservation(List<Book> books, ServerWebExchange serverRequest);
    Mono<Reservation> deleteReservation(Reservation reservation);
    Mono<Reservation> updateDate(Reservation reservation);
    Flux<Void>  deleteReservationsExpired();
    Flux<ReservationDto> showAllReservation();

}
