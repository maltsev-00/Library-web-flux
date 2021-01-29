package com.library.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "books")
public class Book {

    @Id
    @NotNull(message = "ID not can be null")
    private UUID id;

    @Length(min = 3, max = 50,message = "Length min = 3, max = 50")
    private String name;

    @Size(min = 1,max = 3, message = "Size authors can be min = 1, max = 3")
    private List<@Valid Author> authors;

    @Length(min = 5,max = 50,message = "Length can be 50 max, min 5")
    private String publisher;

    @NotEmpty(message = "Year can not be empty")
   // @JsonFormat(pattern = "yyyy")
    private String year;

    @Size(min = 1,max = 3, message = "Translators can be min = 1 , max = 3")
    private List<@NotBlank String> translators;

    @NotEmpty
    @Length(min =5 ,max = 50,message = "Length can be 50 max and min = 5")
    private String description;

    @Size(min = 1,max = 3, message = "Size genre can be min = 1, max = 3")
    private List<@NotBlank String> genre;

    @NotNull(message = "Available not can be null")
    private Boolean available;

}
