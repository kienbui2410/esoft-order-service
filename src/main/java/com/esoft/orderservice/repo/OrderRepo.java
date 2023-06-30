package com.esoft.orderservice.repo;

import com.esoft.orderservice.model.Order;
import com.esoft.orderservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepo extends JpaRepository<Order, Long>,OrderCustomerRepo {
    List<Order> findByUserId(Long userId);

    @Query(value = "select count(*) from esoft_order where user_id = :userId", nativeQuery = true)
    Long countOrder(@Param("userId") Long userId);

    @Query(value = "select count(*) from esoft_order where user_id = :userId and YEAR(create_at) = :year", nativeQuery = true)
    Long countOrderByPeriod(@Param("userId") Long userId, @Param("year") Integer year);

    @Query(value = "select count(*) from esoft_order where user_id = :userId and YEAR(create_at) = :year and MONTH(create_at) = :month", nativeQuery = true)
    Long countOrderByPeriod(@Param("userId") Long userId, @Param("year") Integer year, @Param("month") Integer month);

    @Query(value = "select sum(quantity) from esoft_order where user_id = :userId", nativeQuery = true)
    Long getRevenueOrder(@Param("userId") Long userId);

    @Query(value = "select sum(quantity) from esoft_order where user_id = :userId and YEAR(create_at) = :year", nativeQuery = true)
    Long getRevenueOrderByPeriod(@Param("userId") Long userId, @Param("year") Integer year);

    @Query(value = "select sum(quantity) from esoft_order where user_id = :userId and YEAR(create_at) = :year and MONTH(create_at) = :month", nativeQuery = true)
    Long getRevenueOrderByPeriod(@Param("userId") Long userId, @Param("year") Integer year, @Param("month") Integer month);
}
