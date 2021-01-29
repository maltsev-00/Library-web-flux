package com.library.controller;

import com.library.model.Book;
import com.library.model.Reservation;
import com.library.model.dto.ReservationDto;
import com.library.service.reservations.ReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.List;

@Validated
@RestController
@RequestMapping("reservation")
@Slf4j
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("addReservation")
    public Flux<Reservation> addReservation(@Valid @RequestBody @Size(max = 5) List<Book> books,ServerWebExchange exchange) {
       log.info("Name first book : "+books.get(0));
       return  reservationService.addNewReservation(books,exchange);
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("deleteReservation")
    public Mono<Reservation> deleteReservation(@Valid @RequestBody Reservation reservation) {
         log.info("Reservation for delete  : "+reservation.toString());
         return reservationService.deleteReservation(reservation);
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("renewal")
    public Mono<Reservation> updateDateReservation(@Valid @RequestBody Reservation reservation) {
        log.info("Reservation to update : "+reservation.toString());
        return reservationService.updateDate(reservation);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("deleteAllReservation")
    public  Flux<Void>  deleteAllReservationWhereDateIsExpired(){
        log.info("Delete all reservation where date is expired ");
        return reservationService.deleteReservationsExpired();
    }

    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping("allReservation")
    public Flux<ReservationDto> showAllReservation(){
        log.info("Show all reservation");
        return reservationService.showAllReservation();
    }

}
