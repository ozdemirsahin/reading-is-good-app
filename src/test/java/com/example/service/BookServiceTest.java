package com.example.service;

import com.example.controller.request.AddBookRequest;
import com.example.controller.response.BookDto;
import com.example.entity.Book;
import com.example.repository.BookRepository;
import com.example.service.impl.BookServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class BookServiceTest {

    @InjectMocks
    private BookServiceImpl bookService;

    @Mock
    private BookRepository bookRepository;


    @Test
    void addOrUpdateBook() {

        String name = "Test Book";
        String writer = "Test Writer";
        Integer publishYear = 2021;
        Integer stock = 100;
        Double price = 10.5;

        AddBookRequest request = new AddBookRequest(name, writer, publishYear, stock, price);

        Book book = Book.builder()
                .name(name)
                .writer(writer)
                .publishYear(publishYear)
                .stock(stock)
                .price(price)
                .build();

        when(bookRepository.findByNameAndWriterAndPublishYear(name, writer, publishYear))
                .thenReturn(Optional.empty());

        when(bookRepository.save(any(Book.class))).thenReturn(book);

        BookDto bookDto = bookService.addBook(request);

        assertNotNull(bookDto);
        assertEquals(bookDto.getName(), book.getName());
        assertEquals(bookDto.getPrice(), book.getPrice());


    }

    @Test
    void updateBookStock() {

        String bookId = "b100";
        int newStock = 100;
        int existingStock= 1;

        Book book = Book.builder().stock(1).build();
        Book updatedBook = Book.builder().stock(newStock + existingStock).build();


        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(bookRepository.save(any(Book.class))).thenReturn(updatedBook);

        BookDto bookDto = bookService.updateBookStock(bookId, newStock);


        assertNotNull(bookDto);
        assertEquals(existingStock + newStock, bookDto.getStock());
    }
}
