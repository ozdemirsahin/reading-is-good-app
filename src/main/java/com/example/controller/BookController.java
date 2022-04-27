package com.example.controller;

import com.example.constants.EndPoints;
import com.example.controller.request.AddBookRequest;
import com.example.controller.response.BookDto;
import com.example.service.BookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping(value = EndPoints.BOOK_API, produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Api(value="Book Api Documentation")
public class BookController {

    private final BookService bookService;

    @PostMapping("/add")
    @ApiOperation(value = "Add book method",notes="Use this method for book adding")
    public ResponseEntity<BookDto> addBook(@Valid @RequestBody AddBookRequest addBookRequest) throws Exception {

        log.info("Add new book request is received");

        BookDto bookDto = bookService.addBook(addBookRequest);

        if (bookDto != null) {
            return new ResponseEntity<>(bookDto, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping ("/update/{bookId}")
    @ApiOperation(value = "Update book stock method",notes="Use this method for update the stock of a book")
    public ResponseEntity<BookDto> updateBookStock(@PathVariable String bookId, @RequestParam Integer stockCount) throws Exception {

        log.info("Update book stock request is received");

        BookDto updatedBook = bookService.updateBookStock(bookId, stockCount);

        if (updatedBook != null) {
            return new ResponseEntity<>(updatedBook, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
