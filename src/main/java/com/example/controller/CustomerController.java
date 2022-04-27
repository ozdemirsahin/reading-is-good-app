package com.example.controller;

import com.example.constants.EndPoints;
import com.example.controller.request.CreateCustomerRequest;
import com.example.controller.response.CustomerDto;
import com.example.controller.response.OrderDto;
import com.example.service.CustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping(value = EndPoints.CUSTOMER_API, produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Api(value="Customer Api Documentation")
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping("/create")
    @ApiOperation(value = "Create customer method",notes="Use this method for customer creating")
    public ResponseEntity<CustomerDto> createCustomer(@Valid @RequestBody CreateCustomerRequest createCustomerRequest) throws Exception{

        log.info("createCustomer request is received");
        CustomerDto customerDto = customerService.createCustomer(createCustomerRequest);

        if (customerDto != null) {
            return new ResponseEntity<>(customerDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}/orders")
    @ApiOperation(value = "Get customer orders method",notes="Use this method for list customer orders")
    public ResponseEntity<List<OrderDto>> getCustomerOrders(@PathVariable String id, @PageableDefault(size = 20) Pageable pageable) throws Exception {

        log.info("Customer orders request is received with customerId={}", id);

        List<OrderDto> orderDtos = customerService.getCustomerAllOrders(id, pageable);

        if (orderDtos.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(orderDtos, HttpStatus.OK);
    }
}
