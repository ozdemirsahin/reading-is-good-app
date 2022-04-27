package com.example.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@Document(collection = "customers")
public class Customer {

    @Id
    private String id;

    @NotBlank(message = "email is mandatory")
    private String email;

    @NotBlank(message = "password is mandatory")
    private String password;

    @NotBlank(message = "firstName is mandatory")
    private String fistName;

    private String lastName;

    private String phone;

    private String address;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

}
