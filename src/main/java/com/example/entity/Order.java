package com.example.entity;

import com.example.controller.request.BookOrder;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@Document(collection = "orders")
public class Order {
    @Id
    private String id;

    private String customerId;

    private List<BookOrder> bookOrders;

    private Integer totalBooks;

    private Double totalPrice;

    private String status;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
}
