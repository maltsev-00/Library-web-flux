package com.library.service.books;

import com.library.mappers.BookMapper;
import com.library.model.Author;
import com.library.model.Book;
import com.library.model.dto.BookDto;
import com.library.repository.BookRepository;
import com.library.service.dao.book.BookDao;
import com.library.service.valid.Validation;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;


@Service
@Data
@Slf4j
@ConditionalOnProperty(name = "service.database.book", havingValue = "mongo")
public class BookServiceImpl implements BookService , BookDao {

    private final BookRepository bookRepository;
    private final Validation validator;
    private final BookMapper bookMapper;

    @Override
    public Mono<Object> addNewBook(Book newBook){

       log.info("Kafka send new book :"+newBook.toString());

        if(validator.validNewOperand(newBook)) {
            return bookRepository.findBookByNameOrPublisherOrAuthorsOrTranslatorsOrYear(newBook.getName(),
                    newBook.getPublisher(),
                    newBook.getAuthors(),
                    newBook.getTranslators(),
                    newBook.getYear())
                    .flatMap(error -> Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST,
                            newBook.getName() + " is found in database")))
                    .switchIfEmpty(bookRepository.save(newBook));
        }
           return Mono.just(Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST,
                 newBook.getName()+" is not valid ")));

    }


    @Override
    public Flux<Book> findAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Flux<BookDto> freeBooks() {
        return  bookRepository.findAll()
                .filter(book->book.getAvailable().equals(true))
                .map(bookMapper::toBookDto);
    }

    @Override
    public Flux<Book> searchBooksByAuthor(List<Author> namesAuthor) {
        return bookRepository.findListBooksByAuthors(namesAuthor);
    }

    @Override
    public Flux<Book> searchBooksByGenre(List<String> namesGenre) {
        return bookRepository.findBooksByGenre(namesGenre);
    }

    @Override
    public Flux<Book> searchBookTranslator(List<String> translator) {
        return bookRepository.findBooksByTranslators(translator);
    }

    @Override
    public Mono<Book> updateBook(Book book,Boolean available) {
        log.info(book.getName()+" was found in database ");
        book.setAvailable(available);
        return  bookRepository.save(book);
    }

    @Override
    public Mono<Book> findBookById(UUID id) {
        return bookRepository.findById(id);
    }

    @Override
    public Mono<Book> findBookByIdAndAvailableTrue(UUID id) {
        return bookRepository.findBookByIdAndAvailableTrue(id);
    }

}
