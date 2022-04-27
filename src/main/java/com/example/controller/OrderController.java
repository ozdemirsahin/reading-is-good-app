package com.example.controller;

import com.example.constants.EndPoints;
import com.example.controller.request.CreateOrderRequest;
import com.example.controller.response.OrderDto;
import com.example.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping(value = EndPoints.ORDER_API, produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Api(value="Order Api Documentation")
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/create")
    @ApiOperation(value = "Create order method",notes="Use this method for order creating")
    public ResponseEntity<OrderDto> createOrder(@Valid @RequestBody CreateOrderRequest createOrderRequest) throws Exception{

        log.info("Create order request is received for customer={}", createOrderRequest.getCustomerId());

        OrderDto orderDto = orderService.createOrder(createOrderRequest);
        if (orderDto != null) {
            return new ResponseEntity<>(orderDto, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get order method",notes="Use this method for get an order by id")
    public ResponseEntity<OrderDto> getOrder(@PathVariable String id) throws Exception {

        log.info("Order query by id={} is received", id);

        OrderDto orderDto = orderService.getOrderById(id);
        if (orderDto == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(orderDto, HttpStatus.OK);
    }

    @GetMapping("/getOrdersByDateInterval")
    @ApiOperation(value = "Get orders by date interval method",notes="Use this method to get orders between requested dates")
    public ResponseEntity<List<OrderDto>> getOrdersByDateInterval(@DateTimeFormat(iso = DATE) @RequestParam LocalDate startDate,
                                                    @DateTimeFormat(iso = DATE) @RequestParam LocalDate endDate) throws Exception {
        log.info("Orders query by startDate={} endDate={} is received", startDate, endDate);

        List<OrderDto> orders = orderService.getOrdersByDate(startDate.atStartOfDay(), endDate.atStartOfDay());
        if (orders == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }
}
