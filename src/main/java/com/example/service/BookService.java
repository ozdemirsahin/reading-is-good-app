package com.example.service;

import com.example.controller.request.AddBookRequest;
import com.example.controller.response.BookDto;
import com.example.entity.Book;

public interface BookService {

    BookDto addBook(AddBookRequest addBookRequest);

    BookDto updateBookStock(String bookId, Integer stockValue);

    Book getExistBookForRequestedQuantity(String bookId, Integer quantity);
}
