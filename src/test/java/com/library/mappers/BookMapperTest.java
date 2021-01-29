package com.library.mappers;

import com.library.model.Book;
import com.library.model.dto.BookDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
class BookMapperTest {

    @Autowired
    private Book book;

    @Autowired
    private BookMapper bookMapper;

    @Test
    void toBookDto() {
        BookDto bookDto = bookMapper.toBookDto(book);
        assertNotNull(bookDto);
        assertNotNull(bookDto.getId());
        assertNotNull(bookDto.getName());
        assertNotNull(bookDto.getAuthors());
        assertNotNull(bookDto.getPublisher());
        assertNotNull(bookDto.getTranslators());
        assertNotNull(bookDto.getGenre());
        log.info("BookDto is success");
    }
}