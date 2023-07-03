package com.esoft.orderservice.service;

import com.esoft.orderservice.common.DateUtil;
import com.esoft.orderservice.model.Order;
import com.esoft.orderservice.model.User;
import com.esoft.orderservice.repo.OrderRepo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import java.math.BigDecimal;
import java.util.Date;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    @Mock
    OrderRepo orderRepo;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateOrder_returnsNewOrder() {

        Order mockOrder = Order.builder()
                .id(1L)
                .userId(1L)
                .createdAt(new Date())
                .category(Order.Category.SUPER_LUXURY)
                .quantity(2L)
                .serviceName(Order.ServiceName.VIDEO_EDITING)
                .desc("unit test")
                .note("unit test")
                .price(new BigDecimal("100000"))
                .build();

        Mockito.when(orderRepo.save(Mockito.any(Order.class))).thenReturn(mockOrder);

        Order order = orderService.create(new Order(), new User());

        //created order is not null
        assertThat(order, is(notNullValue()));

        //ref is unique
        String ref = "ODR-"+ DateUtil.convertDateToString(new Date()) + "-1";
        assertThat(order.getRef(), is(ref));
    }

    @Test
    public void testUpdateOrder_returnsOrderNotExisted() {

        Order mockOrder = Order.builder()
                .id(1L)
                .userId(1L)
                .createdAt(new Date())
                .category(Order.Category.SUPER_LUXURY)
                .quantity(2L)
                .serviceName(Order.ServiceName.VIDEO_EDITING)
                .desc("unit test")
                .note("unit test")
                .price(new BigDecimal("100000"))
                .build();

        User mockUser = new User();
        mockUser.setId(2L);

        Mockito.when(orderRepo.existsById(Mockito.any(Long.class))).thenReturn(false);

        try {
            Order order = orderService.update(mockOrder, mockUser);
        }catch (Exception e){
            System.out.println(e.getMessage());
            assertThat(e.getMessage(), is("Order is not existed"));
        }
    }

    @Test
    public void testUpdateOrder_returnsOrderNotBelongUser() {

        Order mockOrder = Order.builder()
                .id(1L)
                .userId(1L)
                .createdAt(new Date())
                .category(Order.Category.SUPER_LUXURY)
                .quantity(2L)
                .serviceName(Order.ServiceName.VIDEO_EDITING)
                .desc("unit test")
                .note("unit test")
                .price(new BigDecimal("100000"))
                .build();

        User mockUser = new User();
        mockUser.setId(2L);

        Mockito.when(orderRepo.existsById(Mockito.any(Long.class))).thenReturn(true);

        try {
            Order order = orderService.update(mockOrder, mockUser);
        }catch (Exception e){
            System.out.println(e.getMessage());
            assertThat(e.getMessage(), startsWith("Order is not belong to the user"));
        }
    }

    @Test
    public void testDeleteOrder_returnsOrderNotExisted() {
        User mockUser = new User();
        mockUser.setId(2L);

        Mockito.when(orderRepo.existsById(Mockito.any(Long.class))).thenReturn(false);

        try {
            orderService.delete(Mockito.anyLong(), mockUser);
        }catch (Exception e){
            System.out.println(e.getMessage());
            assertThat(e.getMessage(), is("Order is not existed"));
        }
    }

    @Test
    public void testDeleteOrder_returnsOrderNotBelongUser() {

        Order mockOrder = Order.builder()
                .id(1L)
                .userId(1L)
                .createdAt(new Date())
                .category(Order.Category.SUPER_LUXURY)
                .quantity(2L)
                .serviceName(Order.ServiceName.VIDEO_EDITING)
                .desc("unit test")
                .note("unit test")
                .price(new BigDecimal("100000"))
                .build();

        User mockUser = new User();
        mockUser.setId(2L);

        Mockito.when(orderRepo.existsById(Long.valueOf(2L))).thenReturn(true);
        Mockito.when(orderRepo.getOne(Mockito.any(Long.class))).thenReturn(mockOrder);

        try {
            orderService.delete(Long.valueOf(2L), mockUser);
        }catch (Exception e){
            System.out.println(e.getMessage());
            assertThat(e.getMessage(), startsWith("Order is not belong to the user"));
        }
    }
}
