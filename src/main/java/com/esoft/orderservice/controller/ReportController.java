package com.esoft.orderservice.controller;


import com.esoft.orderservice.aspect.TrackExecutionTime;
import com.esoft.orderservice.common.AppConstants;
import com.esoft.orderservice.model.payload.ReportResponse;
import com.esoft.orderservice.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.esoft.orderservice.controller.CommonController;

@RestController
@RequestMapping("/reports")
@Slf4j
public class ReportController extends CommonController{

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    OrderService orderService;

    @TrackExecutionTime
    @GetMapping("/users/{id}/order-number")
    public ResponseEntity<?> getNoOrder(@PathVariable("id") Long userId){
        try{
            logger.info("getNoOrder user " + userId);
            Long noOfOrder = orderService.countOrder(userId);
            return toSuccessResult(noOfOrder);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return toExceptionResult(e.getMessage(), AppConstants.API_RESPONSE.RETURN_CODE_ERROR);
        }
    }

    @TrackExecutionTime
    @GetMapping("/users/{id}/revenue")
    public ResponseEntity<?> getRevenue(@PathVariable("id") Long userId){
        try{
            logger.info("revenues user " + userId);
            Long rev = orderService.getRevenueOrder(userId);
            return toSuccessResult(rev);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return toExceptionResult(e.getMessage(), AppConstants.API_RESPONSE.RETURN_CODE_ERROR);
        }
    }

    @TrackExecutionTime
    @GetMapping("/order-revenue-summary/year/{year}")
    public ResponseEntity<?> getOrderRevenueSummary(@PathVariable("year") Integer year){
        logger.info("order-revenue-summary year " + year);
        Long noOfOrder = orderService.countOrderByPeriod(year);
        Long rev = orderService.getRevenueOrderByPeriod(year);
        return toSuccessResult(new ReportResponse(noOfOrder, rev));
    }

    @TrackExecutionTime
    @GetMapping("/order-revenue-summary/year/{year}/month/{month}")
    public ResponseEntity<?> getOrderRevenueSummary(@PathVariable("year") Integer year,@PathVariable("month") Integer month){
        logger.info("order-revenue-summary year " + year + " month " + month);
        Long noOfOrder = orderService.countOrderByPeriod(year,month);
        Long rev = orderService.getRevenueOrderByPeriod(year,month);
        return toSuccessResult(new ReportResponse(noOfOrder, rev));
    }

}
