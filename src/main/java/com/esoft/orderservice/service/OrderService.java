package com.esoft.orderservice.service;

import com.esoft.orderservice.model.Order;
import com.esoft.orderservice.model.User;
import com.esoft.orderservice.repo.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    OrderRepo orderRepo;

    public Order create(Order order, User user){
        order.setUserId(user.getId());
        order.setCreatedAt(new Date());
        return orderRepo.save(order);
    }

    public Order update(Order order, User user) throws Exception{
        if(orderRepo.existsById(order.getId())) {
            if(order.getUserId().compareTo(user.getId()) == 0) {
                return orderRepo.save(order);
            }else{
                throw new Exception("Order is not belong to the user "+user.getUsername());
            }
        }else{
            throw new Exception("Order is not existed");
        }
    }

    public void delete(Long orderId, User user) throws Exception{
        if(orderRepo.existsById(orderId)) {
            Order order = orderRepo.getOne(orderId);
            if(order.getUserId().compareTo(user.getId()) == 0) {
                orderRepo.deleteById(orderId);
            }else{
                throw new Exception("Order is not belong to the user "+user.getUsername());
            }
        }else{
            throw new Exception("Order is not existed");
        }
    }

    public List<Order> list(){
        return orderRepo.findAll();
    }

    public List<Order> listByUserId(Long userId){
        return orderRepo.findByUserId(userId);
    }

    public Long countOrder(Long userId){
        return orderRepo.countOrder(userId);
    }

    public Long countOrder(Long userId, Integer year){
        return orderRepo.countOrderByPeriod(userId, year);
    }

    public Long countOrder(Long userId, Integer year, Integer month){
        return orderRepo.countOrderByPeriod(userId, year, month);
    }

    public Long getRevenueOrder(Long userId){
        return orderRepo.getRevenueOrder(userId);
    }

    public Long getRevenueOrder(Long userId, Integer year){
        return orderRepo.getRevenueOrderByPeriod(userId, year);
    }

    public Long getRevenueOrder(Long userId, Integer year, Integer month){
        return orderRepo.getRevenueOrderByPeriod(userId, year, month);
    }
}
