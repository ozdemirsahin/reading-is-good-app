package com.example.service.impl;

import com.example.constants.Errors;
import com.example.controller.request.BookOrder;
import com.example.controller.request.CreateOrderRequest;
import com.example.controller.response.OrderDto;
import com.example.entity.Book;
import com.example.entity.Customer;
import com.example.entity.Order;
import com.example.exception.CustomerNotFoundException;
import com.example.exception.OrderNotFoundException;
import com.example.exception.StockNotAvailableException;
import com.example.repository.CustomerRepository;
import com.example.repository.OrderRepository;
import com.example.service.BookService;
import com.example.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final BookService bookService;
    private final ReentrantLock reentrantLock = new ReentrantLock();

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OrderDto createOrder(CreateOrderRequest createOrderRequest){

        try {
            if (reentrantLock.tryLock(10, TimeUnit.SECONDS)) {
                String customerId = createOrderRequest.getCustomerId();
                Optional<Customer> optCustomer = customerRepository.findById(customerId);

                if (optCustomer.isEmpty()) {
                    log.error("Order is not created due to lack of requested books");
                    throw new CustomerNotFoundException(Errors.CUSTOMER_NOT_FOUND);
                }

                List<BookOrder> bookOrderList = createOrderRequest.getBookOrders();

                // fetch books by id and controlling whether or not there is enough stock for each book order
                List<Book> existBookList = bookOrderList.stream()
                        .map(bookOrder -> bookService.getExistBookForRequestedQuantity(bookOrder.getBookId(), bookOrder.getCount()))
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList());

                if (existBookList.size() < bookOrderList.size()) {
                    log.error("Order is not created due to lack of requested books");
                    throw new StockNotAvailableException(Errors.STOCK_NOT_AVAILABLE);
                }

                Map<String, Integer> bookOrderMap = bookOrderList.stream()
                        .collect(Collectors.toMap(BookOrder::getBookId, BookOrder::getCount));

                // update each book stock
                existBookList.forEach(existBook -> bookService.updateBookStock(existBook.getId(), existBook.getStock() - bookOrderMap.get(existBook.getId())));

                // calculate total order price
                Double totalPrice = existBookList.stream()
                        .map(existBook -> bookOrderMap.get(existBook.getId()) * existBook.getPrice())
                        .reduce(0.0, Double::sum);

                // calculate total book count
                Integer totalBooks = bookOrderMap.values().stream()
                        .reduce(0, Integer::sum);

                // create new order
                Order order = Order.builder()
                        .customerId(customerId)
                        .bookOrders(bookOrderList)
                        .totalBooks(totalBooks)
                        .totalPrice(totalPrice)
                        .status("COMPLETED")
                        .build();

                Order savedOrder = orderRepository.save(order);

                log.info("Order is created successfully with customer={} books={}", customerId, existBookList);

                return OrderDto.fromOrder(savedOrder);
            }
        }catch (InterruptedException e) {
                throw new RuntimeException(e);
        } finally {
            reentrantLock.unlock();
        }

        return null;
    }


    @Override
    public OrderDto getOrderById(String orderId){

        final Optional<Order> orderOptional = orderRepository.findById(orderId);
        if (orderOptional.isEmpty()) {
            log.error("Order is not found with id={}", orderId);
           throw new OrderNotFoundException(Errors.ORDER_NOT_FOUND + ":"+ orderId);
        }
        return OrderDto.fromOrder(orderOptional.get());
    }

    @Override
    public List<OrderDto> getOrdersByDate(LocalDateTime startDate, LocalDateTime endDate){
        List<Order> orders = orderRepository.findByCreatedAtBetween(startDate, endDate);

        return orders.stream()
                .map(OrderDto::fromOrder)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderDto> getOrdersByCustomerId(String customerId, Pageable pageable){

        Page<Order> pageOrders = orderRepository.findByCustomerId(customerId, pageable);
        List<Order> customerOrders = pageOrders.getContent();

        return customerOrders.stream()
                .map(OrderDto::fromOrder)
                .collect(Collectors.toList());
    }
}
