package com.example.service;

import com.example.controller.response.CustomerMonthlyStatisticsDto;

import java.util.List;

public interface StatisticsService {

    List<CustomerMonthlyStatisticsDto> getCustomerMonthlyStatistics(String customerId) throws Exception;
}
