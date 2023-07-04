package com.esoft.orderservice.controller;


import com.esoft.orderservice.aspect.TrackExecutionTime;
import com.esoft.orderservice.common.AppConstants;
import com.esoft.orderservice.model.payload.OrderResponse;
import com.esoft.orderservice.model.CustomUserDetails;
import com.esoft.orderservice.model.Order;
import com.esoft.orderservice.model.User;
import com.esoft.orderservice.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/orders")
@Slf4j
public class OrderController extends CommonController{

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    OrderService orderService;

    @TrackExecutionTime
    @PostMapping
    public ResponseEntity<?> createOrder(@Valid  @RequestBody Order order) {
        try {
            User user = ((CustomUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
            logger.info("createOrder user " + user);
            order = orderService.create(order,user);
            return toSuccessResult(order);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return toExceptionResult(e.getMessage(), AppConstants.API_RESPONSE.RETURN_CODE_ERROR);
        }
    }

    @PutMapping
    public ResponseEntity<?> updateOrder(@Valid @RequestBody Order order) {
        try {
            User user = ((CustomUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
            logger.info("updateOrder user " + user);
            order = orderService.update(order,user);
            return toSuccessResult(order);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return toExceptionResult(e.getMessage(), AppConstants.API_RESPONSE.RETURN_CODE_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable("id") long orderId) {
        try {
            User user = ((CustomUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
            logger.info("deleteOrder user " + user);
            orderService.delete(orderId,user);
            return toSuccessResult("","deleted order id "+orderId+" successful");
        } catch (Exception e) {
            logger.error(e.getMessage());
            return toExceptionResult(e.getMessage(), AppConstants.API_RESPONSE.RETURN_CODE_ERROR);
        }
    }

    @TrackExecutionTime
    @GetMapping
    public ResponseEntity<?> getAllOrderPagination(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.PAGINATION.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGINATION.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.PAGINATION.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.PAGINATION.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ){
        return toSuccessResult(orderService.list(pageNo, pageSize, sortBy, sortDir));
    }

}
