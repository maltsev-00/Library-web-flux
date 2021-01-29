package com.library.config.database;

import com.library.model.Reservation;
import com.library.repository.ReservationRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;


public class ReservationDatabase implements DatabaseInit{

    @Autowired
    private  ReservationRepository reservationRepository;

    @Autowired
    private Reservation reservation;

    @PostConstruct
    @Override
    public void initialize() {
         reservationRepository.save(reservation).subscribe();
    }
}
