package com.example.exception;


public class CustomerExistException extends RuntimeException {
    public CustomerExistException(String param){
        super(param);
    }
}
