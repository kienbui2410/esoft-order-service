package com.esoft.orderservice.repo;

public interface OrderCustomerRepo {

    Long countOrder(Long userId);
    Long countOrderByPeriod(Long userId, Integer year);
    Long countOrderByPeriod(Long userId, Integer year, Integer month);
    Long getRevenueOrder(Long userId);
    Long getRevenueOrderByPeriod(Long userId, Integer year);
    Long getRevenueOrderByPeriod(Long userId, Integer year, Integer month);
}
