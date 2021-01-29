package com.library.mappers;

import com.library.model.Reservation;
import com.library.model.dto.ReservationDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = Reservation.class)
public interface ReservationMapper {

    ReservationDto toReservationDto(Reservation book);
}
