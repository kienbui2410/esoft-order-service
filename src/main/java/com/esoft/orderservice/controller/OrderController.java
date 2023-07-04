package com.esoft.orderservice.controller;


import com.esoft.orderservice.aspect.TrackExecutionTime;
import com.esoft.orderservice.common.AppConstants;
import com.esoft.orderservice.helper.payload.OrderResponse;
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
import java.util.List;

@RestController
//@RequestMapping("/orders")
@Slf4j
public class OrderController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    OrderService orderService;

    @TrackExecutionTime
    @PostMapping("/orders")
    public ResponseEntity<Order> createOrder(@Valid  @RequestBody Order order) {
        try {
            User user = ((CustomUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
            logger.info("createOrder user " + user);
            order = orderService.create(order,user);
            return new ResponseEntity<>(order, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/orders")
    public ResponseEntity<Order> updateOrder(@Valid @RequestBody Order order) {
        try {
            User user = ((CustomUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
            logger.info("updateOrder user " + user);
            order = orderService.update(order,user);
            return new ResponseEntity<>(order, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/orders/{id}")
    public ResponseEntity<HttpStatus> deleteOrder(@PathVariable("id") long orderId) {
        try {
            User user = ((CustomUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
            logger.info("deleteOrder user " + user);
            orderService.delete(orderId,user);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    @TrackExecutionTime
//    @GetMapping("/orders")
//    public ResponseEntity<List<Order>> getAllOrder(){
//        try{
//            User user = ((CustomUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
//            logger.info("listByUserId user " + user);
//            List<Order> list =  orderService.listByUserId(user.getId());
//            return new ResponseEntity<>(list, HttpStatus.OK);
//        } catch (Exception e) {
//            logger.error(e.getMessage());
//            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

    @TrackExecutionTime
    @GetMapping("/orders")
    public OrderResponse getAllOrderPagination(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ){
        return orderService.list(pageNo, pageSize, sortBy, sortDir);
    }

}
