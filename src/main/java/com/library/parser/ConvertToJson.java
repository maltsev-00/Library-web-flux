package com.library.parser;

import com.google.gson.Gson;
import com.library.model.Book;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class ConvertToJson implements Convert{

    private final Gson gson;

    @Override
    public Book convert(String bookStr) { return gson.fromJson(bookStr,Book.class); }

}
