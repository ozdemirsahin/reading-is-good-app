package com.example.controller.response;

import com.example.entity.Book;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class BookDto {

    private String name;

    private String writer;

    private Integer publishYear;

    private Integer stock;

    private Double price;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;


    public static BookDto fromBook(Book book) {

        return BookDto.builder()
                .name(book.getName())
                .writer(book.getWriter())
                .publishYear(book.getPublishYear())
                .stock(book.getStock())
                .price(book.getPrice())
                .createdAt(book.getCreatedAt())
                .updatedAt(book.getUpdatedAt())
                .build();
    }

}
