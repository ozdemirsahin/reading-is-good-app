package com.example.exception;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(String param){
        super(param);
    }
}