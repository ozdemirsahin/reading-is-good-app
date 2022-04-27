package com.example.controller;

import com.example.controller.request.BookOrder;
import com.example.controller.request.CreateOrderRequest;
import com.example.controller.response.OrderDto;
import com.example.service.OrderService;
import com.google.gson.Gson;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class OrderControllerTest {
    private MockMvc mockMvc;

    @Autowired
    protected Gson gson;

    @MockBean
    private OrderService orderService;

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
        verifyNoMoreInteractions(orderService);
    }

    @Test
    void createOrder() throws Exception {
        OrderDto orderDto = OrderDto.builder().build();
        when(orderService.createOrder(any(CreateOrderRequest.class))).thenReturn(orderDto);

        List<BookOrder> bookOrders=new ArrayList<>();
        BookOrder bookOrder=new BookOrder("1234",10);
        bookOrders.add(bookOrder);

        CreateOrderRequest request=new CreateOrderRequest("5678",bookOrders);

        this.mockMvc.perform(post("/order/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(request)))
                .andExpect(status().isCreated());

        verify(orderService).createOrder(any(CreateOrderRequest.class));
    }

    @Test
    void getOrder() throws Exception {
        String orderId = "o100L";
        OrderDto orderDto = OrderDto.builder().build();

        when(orderService.getOrderById(orderId)).thenReturn(orderDto);

        this.mockMvc.perform(get("/order/{id}", orderId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));

        verify(orderService).getOrderById(orderId);
    }
}
