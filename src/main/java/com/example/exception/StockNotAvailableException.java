package com.example.exception;

public class StockNotAvailableException extends RuntimeException {
    public StockNotAvailableException(String param){
        super(param);
    }
}