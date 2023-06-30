package com.esoft.orderservice.repo;

public interface OrderCustomerRepo {

    Long countOrder(Long userId);
//    Long countOrderByPeriod(Long userId);
    Long getRevenueOrder(Long userId);
//    Long getRevenueOrderByPeriod(Long userId);


}
