package com.example.controller.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddBookRequest {
    @NotBlank(message = "name is mandotory")
    private String name;

    @NotBlank(message = "writer is mandatory")
    private String writer;

    @NotNull(message = "publishYear is mandatory")
    private Integer publishYear;

    private Integer stock;

    private Double price;
}
