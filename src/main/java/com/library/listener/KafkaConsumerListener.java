package com.library.listener;

import com.library.LibraryApplication;
import com.library.model.Book;
import com.library.parser.Convert;
import com.library.service.books.BookService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.kafka.receiver.ReceiverRecord;

import javax.annotation.PostConstruct;


@Data
@Slf4j
@Component
public class KafkaConsumerListener {

    private final Flux<ReceiverRecord<String,String>> receiverRecordFlux;
    private final BookService bookServiceImpl;
    private final Convert convert;

    @EventListener(LibraryApplication.class)
    @PostConstruct
    public void onMessage(){
        receiverRecordFlux
                .map(book->convert.convert(book.value()))
                .subscribe(bookServiceImpl::addNewBook);
    }

}
