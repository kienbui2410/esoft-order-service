package com.esoft.orderservice.service;

import com.esoft.orderservice.common.DateUtil;
import com.esoft.orderservice.model.payload.OrderResponse;
import com.esoft.orderservice.model.Order;
import com.esoft.orderservice.model.User;
import com.esoft.orderservice.repo.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private static final String REF_PREFIX = "ODR";
    private static final String REF_DELIMITER = "-";

    @Autowired
    OrderRepo orderRepo;

    @Transactional
    public Order create(Order order, User user){
        order.setUserId(user.getId());
        order.setCreatedAt(new Date());
        order =  orderRepo.save(order);

        String ref = REF_PREFIX + REF_DELIMITER + DateUtil.convertDateToString(order.getCreatedAt())
                + REF_DELIMITER + String.valueOf(order.getId());
        order.setRef(ref);
        return orderRepo.save(order);
    }

    @Transactional
    public Order update(Order order, User user) throws Exception{
        if(orderRepo.existsById(order.getId())) {
            Order orderDb = orderRepo.getOne(order.getId());
            if(orderDb.getUserId().compareTo(user.getId()) == 0) {
//                Order orderDb = orderRepo.getOne(order.getId());
//                order.setRef(orderDb.getRef());
//                order.setCreatedAt(orderDb.getCreatedAt());
                return orderRepo.save(order);
            }else{
                throw new Exception("Order is not belong to the user "+user.getUsername());
            }
        }else{
            throw new Exception("Order is not existed");
        }
    }

    @Transactional
    public void delete(Long orderId, User user) throws Exception{
        if(orderRepo.existsById(orderId)) {
            Order orderDb = orderRepo.getOne(orderId);
            if(orderDb.getUserId().compareTo(user.getId()) == 0) {
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

    public OrderResponse list(int pageNo, int pageSize, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        // create Pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Order> orders = orderRepo.findAll(pageable);

        // get content for page object
        List<Order> listOfOrders = orders.getContent();

        List<Order> content= listOfOrders.stream().collect(Collectors.toList());

        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setContent(content);
        orderResponse.setPageNo(orders.getNumber());
        orderResponse.setPageSize(orders.getSize());
        orderResponse.setTotalElements(orders.getTotalElements());
        orderResponse.setTotalPages(orders.getTotalPages());
        orderResponse.setLast(orders.isLast());

        return orderResponse;
    }

    public List<Order> listByUserId(Long userId){
        return orderRepo.findByUserId(userId);
    }

    public Long countOrder(Long userId){
        return orderRepo.countOrder(userId);
    }

    public Long countOrderByPeriod(Integer year){
        return orderRepo.countOrderByPeriod(year);
    }

    public Long countOrderByPeriod(Integer year, Integer month){
        return orderRepo.countOrderByPeriod(year, month);
    }

    public Long getRevenueOrder(Long userId){
        Long revenue = orderRepo.getRevenueOrder(userId);
        if(revenue == null)
            return 0L;
        else
            return revenue;
    }

    public Long getRevenueOrderByPeriod(Integer year){
        return orderRepo.getRevenueOrderByPeriod(year);
    }

    public Long getRevenueOrderByPeriod(Integer year, Integer month){
        return orderRepo.getRevenueOrderByPeriod(year, month);
    }
}
