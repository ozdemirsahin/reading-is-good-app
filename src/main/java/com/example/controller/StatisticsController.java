package com.example.controller;

import com.example.constants.EndPoints;
import com.example.controller.response.CustomerMonthlyStatisticsDto;
import com.example.service.StatisticsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping(value = EndPoints.STATISTICS_API)
@RequiredArgsConstructor
@Api(value="Statistics Api Documentation")
public class StatisticsController {

    private final StatisticsService statisticsService;

    /**
     * Query to month-based customer order data
     * @param customerId id of customer
     * @return list of CustomerMonthlyStaticsDto
     */
    @GetMapping("/{customerId}")
    @ApiOperation(value = "Get customer's monthly statistics",notes="Use this method for get customer’s monthly order statistics")
    public ResponseEntity<List<CustomerMonthlyStatisticsDto>> getCustomerMonthlyStatistics(@PathVariable String customerId) throws Exception{

        log.info("Customer monthly statics request is received with customerId={}", customerId);

        List<CustomerMonthlyStatisticsDto> customerMonthlyStatistics = statisticsService.getCustomerMonthlyStatistics(customerId);
        return new ResponseEntity<>(customerMonthlyStatistics, HttpStatus.OK);

    }
}
