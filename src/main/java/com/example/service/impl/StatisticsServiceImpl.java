package com.example.service.impl;

import com.example.controller.response.CustomerMonthlyStatisticsDto;
import com.example.controller.response.OrderDto;
import com.example.service.CustomerService;
import com.example.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Month;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class StatisticsServiceImpl implements StatisticsService {

    private final CustomerService customerService;

    @Override
    public List<CustomerMonthlyStatisticsDto> getCustomerMonthlyStatistics(String customerId) throws Exception {

        // get customer al orders
        List<OrderDto> customerOrders = customerService.getCustomerAllOrders(customerId, Pageable.unpaged());

        // group customer orders by month
        Map<Month, List<OrderDto>> result = customerOrders.stream()
                .collect(Collectors.groupingBy(order -> order.getCreatedAt().getMonth()));

        return result.entrySet().stream()
                .map(entry -> createCustomerMonthlyStatics(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    /**
     * Helper method to calculate total book and total amount for each month orders
     * @param month java.time.Month
     * @param orderList list of order
     * @return CustomerMonthlyStaticsDto
     */
    private CustomerMonthlyStatisticsDto createCustomerMonthlyStatics(Month month, List<OrderDto> orderList) {
        int totalBookCount = 0;
        double totalPurchasedAmount = 0;

        for (OrderDto order : orderList) {

            totalBookCount += order.getTotalBooks();
            totalPurchasedAmount += order.getTotalPrice();
        }

        return new CustomerMonthlyStatisticsDto(month.toString(), orderList.size(), totalBookCount, totalPurchasedAmount);
    }
}
