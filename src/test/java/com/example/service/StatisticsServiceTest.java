package com.example.service;

import com.example.controller.request.BookOrder;
import com.example.controller.response.CustomerMonthlyStatisticsDto;
import com.example.controller.response.OrderDto;
import com.example.service.impl.StatisticsServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class StatisticsServiceTest {

    @InjectMocks
    private StatisticsServiceImpl staticsService;

    @Mock
    private CustomerService customerService;

    @Test
    void getCustomerMonthlyStatics() throws Exception {

        String customerId = "c101L";
        BookOrder bookOrder1 = new BookOrder("1l", 1);
        BookOrder bookOrder2 = new BookOrder("2l", 2);
        BookOrder bookOrder3 = new BookOrder("3l", 3);
        BookOrder bookOrder4 = new BookOrder("4l", 4);

        OrderDto orderDto1 = OrderDto.builder()
                .createdAt(LocalDateTime.of(2021, 10, 10, 12, 30))
                .bookOrders(Arrays.asList(bookOrder1, bookOrder2))
                .totalPrice(30.0)
                .totalBooks(3)
                .build();

        OrderDto orderDto2 = OrderDto.builder()
                .createdAt(LocalDateTime.of(2021, 10, 9, 12, 30))
                .bookOrders(Arrays.asList(bookOrder3, bookOrder4))
                .totalPrice(40.0)
                .totalBooks(7)
                .build();


        when(customerService.getCustomerAllOrders(customerId, Pageable.unpaged())).thenReturn(Arrays.asList(orderDto1, orderDto2));

        List<CustomerMonthlyStatisticsDto> customerMonthlyStatics = staticsService.getCustomerMonthlyStatistics(customerId);

        assertNotNull(customerMonthlyStatics);
        assertEquals(1, customerMonthlyStatics.size());
        assertEquals("OCTOBER", customerMonthlyStatics.get(0).getMonth());
        assertEquals(2, customerMonthlyStatics.get(0).getTotalOrderCount());
        assertEquals(10, customerMonthlyStatics.get(0).getTotalBookCount());
        assertEquals(70, customerMonthlyStatics.get(0).getTotalPurchasedAmount());
    }
}
