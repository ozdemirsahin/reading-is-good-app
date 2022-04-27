package com.example.controller.response;

import com.example.controller.request.BookOrder;
import com.example.entity.Order;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
public class OrderDto {

    private String customerId;

    private List<BookOrder> bookOrders;

    private Integer totalBooks;

    private LocalDateTime createdAt;

    private Double totalPrice;

    private String status;


    public static OrderDto fromOrder(Order order) {

        return OrderDto.builder()
                .customerId(order.getCustomerId())
                .bookOrders(order.getBookOrders())
                .totalBooks(order.getTotalBooks())
                .createdAt(order.getCreatedAt())
                .totalPrice(order.getTotalPrice())
                .status(order.getStatus())
                .build();
    }

}
