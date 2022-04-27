package com.example.exception;

public class BookExistException extends RuntimeException {
    public BookExistException(String param){
        super(param);
    }
}