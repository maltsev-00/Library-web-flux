package com.library.model.dto;

import com.library.model.Book;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationDto {

    @Valid
    @NotNull(message = "Book not can be null")
    private Book book;


    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate date;
}
