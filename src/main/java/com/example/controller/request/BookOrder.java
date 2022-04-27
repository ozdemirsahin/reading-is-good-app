package com.example.controller.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BookOrder {
    @NotBlank(message = "bookId is mandatory")
    private String bookId;

    @Positive(message = "count should be positive number")
    @NotNull(message = "count is mandatory")
    private Integer count;
}
