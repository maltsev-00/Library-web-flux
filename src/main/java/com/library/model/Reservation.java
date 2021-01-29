package com.library.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@Document(collection = "reservation")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Validated
public class Reservation{

    @Id
    @NotNull(message = "Id not can be null")
    private UUID id;

    @NotEmpty(message = "User not can be empty")
    @NotNull(message = "User not can be null")
    private String user;

    @Valid
    @NotNull(message = "Book not can be null")
    private Book book;


    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate date;

}
