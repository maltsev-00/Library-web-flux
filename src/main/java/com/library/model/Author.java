package com.library.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Author {

    @NotEmpty(message = "Name author not can be empty")
    @NotNull(message = "Name author not can be null")
    private String name;

}
