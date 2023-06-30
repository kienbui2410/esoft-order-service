package com.esoft.orderservice.controller;


import com.esoft.orderservice.helper.payload.ReportResponse;
import com.esoft.orderservice.model.CustomUserDetails;
import com.esoft.orderservice.model.Order;
import com.esoft.orderservice.model.User;
import com.esoft.orderservice.service.OrderService;
import org.springframework.util.Assert;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/report")
@Slf4j
public class ReportController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    OrderService orderService;

    @GetMapping("/getNoOrder")
    public ResponseEntity<Long> getNoOrder(@RequestParam(name = "year", required=false) Integer year,
                                           @RequestParam(name = "month", required=false) Integer month){
        try{
            User user = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
            logger.info("getNoOrder user " + user);
            Long noOfOrder = 0L;
            if(year == null) {
                noOfOrder = orderService.countOrder(user.getId());
            } else if(month == null){
                noOfOrder = orderService.countOrder(user.getId(), year);
            } else {
                noOfOrder = orderService.countOrder(user.getId(), year, month);
            }
            return new ResponseEntity<>(noOfOrder, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getRevenue")
    public ResponseEntity<Long> getRevenue(@RequestParam(name = "year", required=false) Integer year,
                                           @RequestParam(name = "month", required=false) Integer month){
        try{
            User user = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
            logger.info("getNoOrder user " + user);
            Long rev;
            if(year == null) {
                rev = orderService.getRevenueOrder(user.getId());
            } else if(month == null){
                rev = orderService.getRevenueOrder(user.getId(), year);
            } else {
                rev = orderService.getRevenueOrder(user.getId(), year, month);
            }
            return new ResponseEntity<>(rev==null?0L:rev, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getAllInfo")
    public ReportResponse getAllInfo(@RequestParam(name = "year", required = false) Integer year,
                                     @RequestParam(name = "month", required = false) Integer month) {
        User user = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        logger.info("getNoOrder user " + user);
        Long noOfOrder = 0L;
        Long rev;
        if (year == null) {
            noOfOrder = orderService.countOrder(user.getId());
            rev = orderService.getRevenueOrder(user.getId());
        } else if (month == null) {
            noOfOrder = orderService.countOrder(user.getId(), year);
            rev = orderService.getRevenueOrder(user.getId(), year);
        } else {
            noOfOrder = orderService.countOrder(user.getId(), year, month);
            rev = orderService.getRevenueOrder(user.getId(), year, month);
        }
        noOfOrder = noOfOrder==null?0:noOfOrder;
        rev = rev==null?0:rev;

        return new ReportResponse(noOfOrder, rev);
    }

}
