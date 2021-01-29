package com.library.mappers;

import com.library.model.Book;
import com.library.model.dto.BookDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = Book.class)
public interface BookMapper {

    BookDto toBookDto(Book book);

}
