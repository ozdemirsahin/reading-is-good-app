package com.example.service;

import com.example.controller.request.CreateOrderRequest;
import com.example.controller.response.OrderDto;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderService {

    OrderDto createOrder(CreateOrderRequest createOrderRequest);

    OrderDto getOrderById(String orderId);

    List<OrderDto> getOrdersByDate(LocalDateTime startDate, LocalDateTime endDate);

    List<OrderDto> getOrdersByCustomerId(String customerId, Pageable pageable);
}
