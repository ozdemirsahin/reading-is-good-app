package com.example.service;

import com.example.controller.request.BookOrder;
import com.example.controller.request.CreateOrderRequest;
import com.example.controller.response.OrderDto;
import com.example.entity.Book;
import com.example.entity.Customer;
import com.example.entity.Order;
import com.example.repository.CustomerRepository;
import com.example.repository.OrderRepository;
import com.example.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class OrderServiceTest {

    @InjectMocks
    private OrderServiceImpl orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private BookService bookService;

    @Test
    void createNewOrder() {

        String customerId = "c101L";
        String bookId1 = "b1L";
        String bookId2 = "b2L";
        BookOrder bookOrder1 = new BookOrder(bookId1, 1);
        BookOrder bookOrder2 = new BookOrder(bookId2, 1);
        List<BookOrder> bookOrders = Arrays.asList(bookOrder1, bookOrder2);

        Order order = Order.builder()
                .customerId(customerId)
                .bookOrders(Arrays.asList(bookOrder1, bookOrder2))
                .build();

        CreateOrderRequest request = new CreateOrderRequest(customerId, bookOrders);

        Customer customer = Customer.builder()
                .email("test@gmail.com")
                .fistName("Test First")
                .lastName("Test Last")
                .build();

        Optional<Customer> customerOptional = Optional.of(customer);

        when(customerRepository.findById(anyString())).thenReturn(customerOptional);

        Book book1 = Book.builder().id(bookId1).stock(10).price(10.0).build();
        Book book2 = Book.builder().id(bookId2).stock(5).price(10.0).build();

        when(bookService.getExistBookForRequestedQuantity(eq(bookOrder1.getBookId()), eq(bookOrder1.getCount())))
                .thenReturn(book1);
        when(bookService.getExistBookForRequestedQuantity(eq(bookOrder2.getBookId()), eq(bookOrder2.getCount())))
                .thenReturn(book2);

        when(orderRepository.save(any(Order.class))).thenReturn(order);

        OrderDto orderDto = orderService.createOrder(request);

        assertNotNull(orderDto);
        assertEquals(customerId, orderDto.getCustomerId());
        assertEquals(bookOrders.size(), orderDto.getBookOrders().size());
    }

    @Test
    void getOrderById() {

        String customerId = "c101L";
        String orderId = "o11L";
        BookOrder bookOrder1 = new BookOrder("1l", 1);
        BookOrder bookOrder2 = new BookOrder("2l", 2);
        final List<BookOrder> bookOrderList = Arrays.asList(bookOrder1, bookOrder2);
        Double totalPrice= 49.9;

        Order order = Order.builder()
                .customerId(customerId)
                .bookOrders(bookOrderList)
                .totalPrice(totalPrice)
                .build();

        when(orderRepository.findById(anyString())).thenReturn(Optional.of(order));

        OrderDto orderDto = orderService.getOrderById(orderId);

        assertNotNull(orderDto);
        assertEquals(customerId, orderDto.getCustomerId());
        assertEquals(bookOrderList.size(), orderDto.getBookOrders().size());
        assertEquals(totalPrice, orderDto.getTotalPrice());

    }

    @Test
    void getOrdersByDate() {

        LocalDateTime startDate = LocalDateTime.of(2021, 10, 1, 12, 30);
        LocalDateTime endDate = LocalDateTime.of(2021, 10, 15, 12, 30);

        Order order1 = Order.builder()
                .createdAt(LocalDateTime.of(2021, 10, 11, 13, 30))
                .build();

        Order order2 = Order.builder()
                .createdAt(LocalDateTime.of(2021, 10, 9, 11, 30))
                .build();

        when(orderRepository.findByCreatedAtBetween(eq(startDate), eq(endDate)))
                .thenReturn(Arrays.asList(order1, order2));

        List<OrderDto> orders = orderService.getOrdersByDate(startDate, endDate);


        assertFalse(orders.isEmpty());
        assertEquals(2, orders.size());
        assertTrue(orders.get(0).getCreatedAt().isAfter(startDate));

    }
}
