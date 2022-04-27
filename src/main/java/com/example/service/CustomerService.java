package com.example.service;

import com.example.controller.request.CreateCustomerRequest;
import com.example.controller.response.CustomerDto;
import com.example.controller.response.OrderDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomerService {
    CustomerDto createCustomer(CreateCustomerRequest createCustomerRequest);

    List<OrderDto> getCustomerAllOrders(String customerId, Pageable pageable) throws Exception;
}
