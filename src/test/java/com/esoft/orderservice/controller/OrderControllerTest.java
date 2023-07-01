//package com.esoft.orderservice.controller;
//
//import com.esoft.orderservice.model.Order;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.Test;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.web.reactive.function.client.WebClient;
//
//@Slf4j
//public class OrderControllerTest {
//
//    Logger logger = LoggerFactory.getLogger(this.getClass());
//
//    @Test
//    public void Test_listByUserId() {
//        WebClient client = WebClient.builder().baseUrl("localhost:8081").build();
////        Flux<Employee> employees =
//        client.get().uri("/orders")
//                .exchange()
//                .doOnSuccess(clientResponse -> {
//                    clientResponse.bodyToFlux(Order.class).subscribe(odr -> logger.error("$$$$$$$$$$$$$" + odr.getRef()));
//                })
//                .doOnError(throwable -> throwable.printStackTrace());
////        employees.subscribe(employee -> System.out.println("ok"));
//    }
//}
