package com.example.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerMonthlyStatisticsDto {

    private String month;

    private Integer totalOrderCount;

    private Integer totalBookCount;

    private Double totalPurchasedAmount;
}
