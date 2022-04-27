package com.example.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class HandleException {

    @ExceptionHandler({CustomerExistException.class})
    public String alreadyExistCustomer(CustomerExistException e){
        return "The customer email already in used by another record! : "+e.getMessage();
    }

    @ExceptionHandler({StockNotAvailableException.class})
    public String stockNotAvailable(StockNotAvailableException e){
        return "Stock not available for requested books ";
    }

    @ExceptionHandler({BookExistException.class})
    public String bookExist(BookExistException e){
        return "The book already registered, you can update its stock! :"+e.getMessage();
    }

    @ExceptionHandler({BookNotFoundException.class})
    public String EntityNotFound(BookNotFoundException e){
        return "Book not found! bookId :"+e.getMessage();
    }


    @ExceptionHandler({OrderNotFoundException.class})
    public String orderNotFound(OrderNotFoundException e){
        return "The Order not found!: "+e.getMessage();
    }

}
