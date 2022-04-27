package com.example.service.impl;

import com.example.constants.Errors;
import com.example.controller.request.AddBookRequest;
import com.example.controller.response.BookDto;
import com.example.entity.Book;
import com.example.exception.BookExistException;
import com.example.exception.BookNotFoundException;
import com.example.repository.BookRepository;
import com.example.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Override
    public BookDto addBook(AddBookRequest addBookRequest) {

        Optional<Book> bookOpt = bookRepository.findByNameAndWriterAndPublishYear(addBookRequest.getName(), addBookRequest.getWriter(), addBookRequest.getPublishYear());

        if (bookOpt.isPresent()) {
            throw new BookExistException(Errors.BOOK_ALREADY_EXIST);
        }

        Book book = Book.builder()
                .name(addBookRequest.getName())
                .writer(addBookRequest.getWriter())
                .publishYear(addBookRequest.getPublishYear())
                .price(addBookRequest.getPrice())
                .stock(addBookRequest.getStock())
                .build();

        Book savedBook = bookRepository.save(book);

        log.info("New Book is added successfully created with name={} write={} publishDate={} stock={} price={}",
                savedBook.getName(), savedBook.getWriter(), savedBook.getPublishYear(), savedBook.getStock(), savedBook.getPrice());

        return BookDto.fromBook(savedBook);
    }

    @Override
    public BookDto updateBookStock(String bookId, Integer stockValue) {

        Optional<Book> bookOptional = bookRepository.findById(bookId);

        if (bookOptional.isEmpty()) {
            log.error("Book not found with id={}", bookId);
            throw new BookNotFoundException(Errors.BOOK_NOT_FOUND);
        }

        Book book = bookOptional.get();
        book.setStock(stockValue);
        Book updatedBook = bookRepository.save(book);

        log.info("Book is updated successfully id={} stockValue={}", bookId, stockValue);

        return BookDto.fromBook(updatedBook);
    }

    @Override
    public Book getExistBookForRequestedQuantity(String bookId, Integer quantity) {
        Optional<Book> bookOpt = bookRepository.findById(bookId);

        if (bookOpt.isEmpty()) {
            log.error("Book is not found with bookId={}", bookId);
            return null;
        } else if (bookOpt.get().getStock() < quantity) {
            log.error("Book does have enough quantity with bookId={} existingStock={} requestedStock={}",
                    bookId, bookOpt.get().getStock(), quantity);
            return null;
        }
        return bookOpt.get();
    }
}
