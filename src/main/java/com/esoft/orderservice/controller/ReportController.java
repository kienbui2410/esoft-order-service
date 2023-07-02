package com.esoft.orderservice.controller;


import com.esoft.orderservice.aspect.TrackExecutionTime;
import com.esoft.orderservice.helper.payload.ReportResponse;
import com.esoft.orderservice.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

@RestController
@RequestMapping("/reports")
@Slf4j
public class ReportController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    OrderService orderService;

    @TrackExecutionTime
    @GetMapping("/users/{id}/order-number")
    public ResponseEntity<Long> getNoOrder(@PathVariable("id") Long userId){
        try{
            logger.info("getNoOrder user " + userId);
            Long noOfOrder = orderService.countOrder(userId);
            return new ResponseEntity<>(noOfOrder, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @TrackExecutionTime
    @GetMapping("/users/{id}/revenue")
    public ResponseEntity<Long> getRevenue(@PathVariable("id") Long userId){
        try{
            logger.info("revenues user " + userId);
            Long rev = orderService.getRevenueOrder(userId);
            return new ResponseEntity<>(rev==null?0L:rev, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @TrackExecutionTime
    @GetMapping("/order-revenue-summary/year/{year}")
    public ReportResponse getOrderRevenueSummary(@PathVariable("year") Integer year){
        logger.info("order-revenue-summary year " + year);
        Long noOfOrder = orderService.countOrderByPeriod(year);
        Long rev = orderService.getRevenueOrderByPeriod(year);
        noOfOrder = noOfOrder==null?0:noOfOrder;
        rev = rev==null?0:rev;

        return new ReportResponse(noOfOrder, rev);
    }

    @TrackExecutionTime
    @GetMapping("/order-revenue-summary/year/{year}/month/{month}")
    public ReportResponse getOrderRevenueSummary(@PathVariable("year") Integer year,@PathVariable("month") Integer month){
        logger.info("order-revenue-summary year " + year + " month " + month);
        Long noOfOrder = orderService.countOrderByPeriod(year,month);
        Long rev = orderService.getRevenueOrderByPeriod(year,month);
        noOfOrder = noOfOrder==null?0:noOfOrder;
        rev = rev==null?0:rev;

        return new ReportResponse(noOfOrder, rev);
    }

}
