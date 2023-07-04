package com.esoft.orderservice.service;

import com.esoft.orderservice.common.DateUtil;
import com.esoft.orderservice.model.Order;
import com.esoft.orderservice.model.User;
import com.esoft.orderservice.model.payload.OrderResponse;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

        Order mockOrderDb = Order.builder()
                .id(1L)
                .userId(1L)
                .build();

        User mockUser = new User();
        mockUser.setId(2L);

        Mockito.when(orderRepo.existsById(Mockito.any(Long.class))).thenReturn(true);
        Mockito.when(orderRepo.getOne(mockOrder.getId())).thenReturn(mockOrderDb);

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

    @Test
    public void testListOrder_returnsOrderPagination() {

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
        List<Order> orderList = new ArrayList<>();
        orderList.add(mockOrder);
        orderList.add(mockOrder);
        orderList.add(mockOrder);
        orderList.add(mockOrder);
        orderList.add(mockOrder);

        Page<Order> page = new PageImpl(orderList, new Pageable() {
            @Override
            public int getPageNumber() {
                return 0;
            }

            @Override
            public int getPageSize() {
                return 5;
            }

            @Override
            public long getOffset() {
                return 0;
            }

            @Override
            public Sort getSort() {
                return null;
            }

            @Override
            public Pageable next() {
                return null;
            }

            @Override
            public Pageable previousOrFirst() {
                return null;
            }

            @Override
            public Pageable first() {
                return null;
            }

            @Override
            public boolean hasPrevious() {
                return false;
            }
        }, 11);

        Mockito.when(orderRepo.findAll(Mockito.any(Pageable.class))).thenReturn(page);

        OrderResponse orderResponse = orderService.list(0, 5, "id", "asc");
        System.out.println(orderResponse);
        assertThat(orderResponse.getContent().size(), is(5));
        assertThat(orderResponse.getPageNo(), is(0));
        assertThat(orderResponse.getPageSize(), is(5));
        assertThat(orderResponse.getTotalElements(), is(11L));
        assertThat(orderResponse.getTotalPages(), is(3));
    }
}
