package com.library.service.reservations;

import com.library.mappers.ReservationMapper;
import com.library.model.Book;
import com.library.model.Reservation;
import com.library.model.dto.ReservationDto;
import com.library.repository.ReservationRepository;
import com.library.service.dao.book.BookDao;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;


@Data
@Service
@Slf4j
@ConditionalOnProperty(name = "service.database.reservation", havingValue = "mongo")
public class ReservationServiceImpl implements ReservationService{

    private final ReservationRepository reservationRepository;
    private final BookDao bookDao;
    private final ReservationMapper mapper;


    public Flux<Reservation> addNewReservation(List<Book> books, ServerWebExchange exchange) {
        return  exchange.getPrincipal().map(Principal::getName)
                  .flatMapMany(name->
                          Flux.fromIterable(books)
                         .flatMap(book->bookDao.findBookByIdAndAvailableTrue(book.getId()))
                         .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST,"Not books is valid ")))
                         .flatMap(book -> bookDao.updateBook(book,false))
                         .flatMap(book->
                                 reservationRepository.save(new Reservation(
                                         UUID.randomUUID(),
                                         name,
                                         book,
                                         LocalDate.now()))));
    }

    public Mono<Reservation> deleteReservation(Reservation reservation) {
        return  reservationRepository.findById(reservation.getId())
                .flatMap(book-> bookDao.findBookById(reservation.getBook().getId())
                .flatMap(bookFound-> bookDao.updateBook(bookFound,true))
                .flatMap(foundBook-> reservationRepository.deleteById(reservation.getId()))
                .thenReturn(reservation))
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND,reservation.getId()+ " is not found")));
    }

    public Mono<Reservation> updateDate(Reservation reservation) {
         return  reservationRepository.findById(reservation.getId())
                  .flatMap(reservationFound -> {
                      reservationFound.setDate(reservationFound.getDate().plusDays(5L));
                      return  reservationRepository.save(reservationFound);
                  })
                  .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, reservation.getId()+" is not found")));

    }

    public Flux<Void> deleteReservationsExpired() {
         return  reservationRepository.findReservationsByDateLessThan(LocalDate.now())
                 .switchIfEmpty(Mono.empty())
                 .flatMap(reservation -> bookDao.updateBook(reservation.getBook(),true))
                 .flatMap(book -> reservationRepository.deleteReservationByBookId(book.getId()));

    }

    public Flux<ReservationDto> showAllReservation() {
         return reservationRepository.findAll().map(mapper::toReservationDto);
    }
}
