package com.esoft.orderservice.repo;

public interface OrderCustomerRepo {

    Long countOrder(Long userId);
    Long countOrderByPeriod(Integer year);
    Long countOrderByPeriod(Integer year, Integer month);
    Long getRevenueOrder(Long userId);
    Long getRevenueOrderByPeriod(Integer year);
    Long getRevenueOrderByPeriod(Integer year, Integer month);
}
