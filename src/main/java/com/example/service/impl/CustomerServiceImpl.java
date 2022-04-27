package com.example.service.impl;

import com.example.constants.Errors;
import com.example.controller.request.CreateCustomerRequest;
import com.example.controller.response.CustomerDto;
import com.example.controller.response.OrderDto;
import com.example.entity.Customer;
import com.example.exception.CustomerExistException;
import com.example.repository.CustomerRepository;
import com.example.service.CustomerService;
import com.example.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    private final OrderService orderService;

    @Override
    public CustomerDto createCustomer(CreateCustomerRequest createCustomerRequest) {

        String email = createCustomerRequest.getEmail();

        Optional<Customer> customerOptional = customerRepository.findByEmail(email);

        if (customerOptional.isPresent()) {
            log.error("Customer already exists with email={}", email);
            throw new CustomerExistException(Errors.CUSTOMER_ALREADY_EXIST+":"+ email);
        }

        Customer customer = Customer.builder()
                .email(email)
                .fistName(createCustomerRequest.getFirstName())
                .lastName(createCustomerRequest.getLastName())
                .password(createCustomerRequest.getPassword())
                .phone(createCustomerRequest.getPhone())
                .address(createCustomerRequest.getAddress())
                .build();

        customerRepository.save(customer);

        log.info("New customer is created with email={}", email);

        return CustomerDto.fromCustomer(customer);

    }

    @Override
    public List<OrderDto> getCustomerAllOrders(String customerId, Pageable pageable) throws Exception {
        return  orderService.getOrdersByCustomerId(customerId, pageable);
    }
}
