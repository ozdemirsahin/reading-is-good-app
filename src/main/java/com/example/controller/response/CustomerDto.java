package com.example.controller.response;

import com.example.entity.Customer;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class CustomerDto {

    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private String address;
    private LocalDateTime createdAt;

    public static CustomerDto fromCustomer(Customer customer) {

        return CustomerDto.builder()
                .email(customer.getEmail())
                .firstName(customer.getFistName())
                .lastName(customer.getLastName())
                .phone(customer.getPhone())
                .address(customer.getAddress())
                .createdAt(customer.getCreatedAt())
                .build();

    }
}
