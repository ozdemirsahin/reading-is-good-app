package com.example.service;

import com.example.controller.request.CreateCustomerRequest;
import com.example.controller.response.CustomerDto;
import com.example.controller.response.OrderDto;
import com.example.entity.Customer;
import com.example.repository.CustomerRepository;
import com.example.service.impl.CustomerServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class CustomerServiceTest {

    @InjectMocks
    private CustomerServiceImpl customerService;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private OrderService orderService;

    @Test
    void createCustomer() {

        String email = "test@gmail.com";
        String firstName = "Test First";
        String lastName = "Test Last";
        String phone = "+905*********";
        String address = "Test Address";

        CreateCustomerRequest request = new CreateCustomerRequest(email, null, firstName, lastName, phone, address);

        Customer customer = Customer.builder()
                .email(email)
                .fistName(firstName)
                .lastName(lastName)
                .phone(phone)
                .address(address)
                .build();

        when(customerRepository.save(any(Customer.class))).thenReturn(customer);
        when(customerRepository.findByEmail(any(String.class))).thenReturn(Optional.empty());

        CustomerDto newCustomer = customerService.createCustomer(request);

        assertNotNull(newCustomer);
        assertEquals(request.getEmail(), newCustomer.getEmail());
        assertEquals(request.getAddress(), newCustomer.getAddress());
        assertEquals(request.getFirstName(), newCustomer.getFirstName());

    }

    @Test
    void getCustomerAllOrders() throws Exception {

        String customerId = "c1001L";
        String status = "Completed";
        LocalDateTime localDateTime = LocalDateTime.of(2021, 10, 11, 13, 30);
        PageRequest pageRequest = PageRequest.of(0, 20);

        OrderDto orderDto = OrderDto.builder()
                .customerId(customerId)
                .createdAt(localDateTime)
                .status(status)
                .build();
        List<OrderDto> orders = Collections.singletonList(orderDto);

        when(orderService.getOrdersByCustomerId(customerId, pageRequest)).thenReturn(orders);

        List<OrderDto> customerAllOrders = customerService.getCustomerAllOrders(customerId, pageRequest);

        assertNotNull(customerAllOrders);
        assertEquals(orders.size(), customerAllOrders.size());
        assertEquals(orders.get(0).getStatus(), customerAllOrders.get(0).getStatus());
    }
}
