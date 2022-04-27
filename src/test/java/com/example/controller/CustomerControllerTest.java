package com.example.controller;

import com.example.controller.request.CreateCustomerRequest;
import com.example.controller.response.CustomerDto;
import com.example.controller.response.OrderDto;
import com.example.service.CustomerService;
import com.google.gson.Gson;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class CustomerControllerTest {
    private static final String EMAIL = "test@gmail.com";
    private static final String FIRST_NAME = "Test First Name";
    private static final String LAST_NAME = "Test Last Name";
    private static final String PHONE = "Test Phone";
    private static final String ADDRESS = "Test Address";

    private static final String PASSWORD = "Test Password";

    private MockMvc mockMvc;

    @Autowired
    protected Gson gson;

    @MockBean
    private CustomerService customerService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach()
    public void setup()
    {
        //Init MockMvc Object and build
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @AfterEach
    public void afterTest()
    {
        verifyNoMoreInteractions(customerService);
    }

    @Test
    void createNewCustomer() throws Exception {
        CustomerDto customerDto = CustomerDto.builder()
                .email(EMAIL)
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .phone(PHONE)
                .address(ADDRESS)
                .build();

        CreateCustomerRequest request = new CreateCustomerRequest(EMAIL, PASSWORD, FIRST_NAME, LAST_NAME, PHONE, ADDRESS);

        when(customerService.createCustomer(any(CreateCustomerRequest.class))).thenReturn(customerDto);

        this.mockMvc.perform(post("/customer/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));

        verify(customerService).createCustomer(any(CreateCustomerRequest.class));
    }

    @Test
    void getCustomerOrders() throws Exception {
        String customerId = "c1L";
        OrderDto orderDto = OrderDto.builder().build();
        List<OrderDto> orderDtoList = Collections.singletonList(orderDto);
        PageRequest pageRequest = PageRequest.of(0, 20);

        when(customerService.getCustomerAllOrders(customerId, pageRequest)).thenReturn(orderDtoList);

        String url = "/customer/create";
        this.mockMvc.perform(get("/customer/{id}/orders", customerId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));

        verify(customerService).getCustomerAllOrders(customerId, pageRequest);
    }
}
