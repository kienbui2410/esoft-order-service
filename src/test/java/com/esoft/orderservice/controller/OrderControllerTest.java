package com.esoft.orderservice.controller;

import com.esoft.orderservice.model.CustomUserDetails;
import com.esoft.orderservice.model.Order;
import com.esoft.orderservice.model.User;
import com.esoft.orderservice.model.payload.ResponseMessage;
import com.esoft.orderservice.repo.OrderRepo;
import com.esoft.orderservice.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringBootTest
public class OrderControllerTest {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    OrderService orderService;

    @Autowired
    OrderRepo orderRepo;

    private static final ObjectMapper mapper = new ObjectMapper();

    CustomUserDetails userDetails;

    @Before
    public void setUp() {
        logger.info(">>>>>>>>>>>>>>>>>>> setup()");
        this.orderRepo.deleteAll();
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac)
                .build();
        User user = new User();
        user.setId(2L);
        this.userDetails = new CustomUserDetails(user);
    }


    @Test
    public void orderController_whenCreateOrder_thenReturnCreatedOrder() throws Exception {

        Order mockOrder = Order.builder()
                .category(Order.Category.SUPER_LUXURY)
                .quantity(2L)
                .serviceName(Order.ServiceName.VIDEO_EDITING)
                .desc("unit test")
                .note("unit test")
                .price(new BigDecimal("100000"))
                .build();

        ResultActions responseBody = this.mockMvc.perform(MockMvcRequestBuilders.post("/orders")
                .with(sessionUser(userDetails))
                .content(asJsonString(mockOrder))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        ResponseMessage response = this.mapper.readValue(responseBody.andReturn().getResponse().getContentAsString(), ResponseMessage.class);
        LinkedHashMap<String,Object> data = (LinkedHashMap<String,Object>)response.getData();
        assertTrue(response.getCode().equals("200"));
        assertNotNull(data.get("id"));
        assertTrue(data.get("userId").toString().equals("2"));
        assertTrue(((String)data.get("ref")).contains("ODR-"));
    }

    @Test
    public void orderController_whenUpdateOrder_thenReturnUpdatedOrder() throws Exception {

        Order mockOrder = Order.builder()
                .category(Order.Category.SUPER_LUXURY)
                .quantity(2L)
                .serviceName(Order.ServiceName.VIDEO_EDITING)
                .desc("unit test")
                .note("unit test")
                .price(new BigDecimal("100000"))
                .build();

        orderService.create(mockOrder, userDetails.getUser());

        Order mockUpdateOrder = Order.builder()
                .id(1L)
                .userId(2L)
                .ref("ODR-20230620-1")
                .category(Order.Category.SUPREME_LUXURY)
                .quantity(2L)
                .serviceName(Order.ServiceName.PHOTO_EDITING)
                .createdAt(new Date())
                .desc("unit test updated")
                .note("unit test updated")
                .price(new BigDecimal("200000"))
                .build();

        ResultActions responseBody = this.mockMvc.perform(MockMvcRequestBuilders.put("/orders")
                .with(sessionUser(userDetails))
                .content(asJsonString(mockUpdateOrder))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
        System.out.println(responseBody);
        ResponseMessage response = this.mapper.readValue(responseBody.andReturn().getResponse().getContentAsString(), ResponseMessage.class);
        LinkedHashMap<String,Object> data = (LinkedHashMap<String,Object>)response.getData();
        assertTrue(response.getCode().equals("200"));
        assertTrue(data.get("category").equals(Order.Category.SUPREME_LUXURY.toString()));
        assertTrue(data.get("serviceName").equals(Order.ServiceName.PHOTO_EDITING.toString()));
        assertTrue(data.get("desc").equals("unit test updated"));
        assertTrue(data.get("note").equals("unit test updated"));
        assertTrue(((Integer)data.get("price")) == 200000);

    }

    @Test
    public void orderController_whenDeleteOrder_thenReturn200Ok() throws Exception {

        Order mockOrder = Order.builder()
                .category(Order.Category.SUPER_LUXURY)
                .quantity(2L)
                .serviceName(Order.ServiceName.VIDEO_EDITING)
                .desc("unit test")
                .note("unit test")
                .price(new BigDecimal("100000"))
                .build();

        Order orderDb = orderService.create(mockOrder, userDetails.getUser());

        ResultActions responseBody = this.mockMvc.perform(MockMvcRequestBuilders.delete("/orders/"+orderDb.getId())
                .with(sessionUser(userDetails))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
        List<Order> list = orderService.list();

        ResponseMessage response = this.mapper.readValue(responseBody.andReturn().getResponse().getContentAsString(), ResponseMessage.class);
        assertTrue(response.getCode().equals("200"));
        assertEquals(list.size(), 0);
    }

    @Test
    public void orderController_whenListOrder_thenOrderList() throws Exception {

        Order mockOrder = Order.builder()
                .category(Order.Category.SUPER_LUXURY)
                .quantity(2L)
                .serviceName(Order.ServiceName.VIDEO_EDITING)
                .desc("unit test")
                .note("unit test")
                .price(new BigDecimal("100000"))
                .build();
        Order mockOrder2 = Order.builder()
                .category(Order.Category.SUPER_LUXURY)
                .quantity(2L)
                .serviceName(Order.ServiceName.VIDEO_EDITING)
                .desc("unit test 2")
                .note("unit test 2")
                .price(new BigDecimal("100000"))
                .build();
        Order mockOrder3 = Order.builder()
                .category(Order.Category.SUPER_LUXURY)
                .quantity(2L)
                .serviceName(Order.ServiceName.VIDEO_EDITING)
                .desc("unit test 3")
                .note("unit test 3")
                .price(new BigDecimal("100000"))
                .build();
        Order mockOrder4 = Order.builder()
                .category(Order.Category.SUPER_LUXURY)
                .quantity(2L)
                .serviceName(Order.ServiceName.VIDEO_EDITING)
                .desc("unit test 4")
                .note("unit test 4")
                .price(new BigDecimal("100000"))
                .build();
        Order mockOrder5 = Order.builder()
                .category(Order.Category.SUPER_LUXURY)
                .quantity(2L)
                .serviceName(Order.ServiceName.VIDEO_EDITING)
                .desc("unit test 5")
                .note("unit test 5")
                .price(new BigDecimal("100000"))
                .build();
        Order mockOrder6 = Order.builder()
                .category(Order.Category.SUPER_LUXURY)
                .quantity(2L)
                .serviceName(Order.ServiceName.VIDEO_EDITING)
                .desc("unit test 6")
                .note("unit test 6")
                .price(new BigDecimal("100000"))
                .build();
        orderService.create(mockOrder, userDetails.getUser());
        orderService.create(mockOrder2, userDetails.getUser());
        orderService.create(mockOrder3, userDetails.getUser());
        orderService.create(mockOrder4, userDetails.getUser());
        orderService.create(mockOrder5, userDetails.getUser());
        orderService.create(mockOrder6, userDetails.getUser());

        ResultActions responseBody = this.mockMvc.perform(MockMvcRequestBuilders.get("/orders?pageNo=0&pageSize=5&sortBy=id&sortDir=asc")
                .with(sessionUser(userDetails))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        List<Order> list = orderService.list();
        ResponseMessage response = this.mapper.readValue(responseBody.andReturn().getResponse().getContentAsString(), ResponseMessage.class);
        LinkedHashMap<String,Object> data = (LinkedHashMap<String,Object>)response.getData();
        assertTrue(response.getCode().equals("200"));
        assertEquals(data.get("totalPages"), 2);
        assertEquals(data.get("totalElements"), 6);
        assertEquals(list.size(), 6);
    }



    public static String asJsonString(final Object obj) {
        try {
//            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static RequestPostProcessor sessionUser(CustomUserDetails userDetails) {
        return new RequestPostProcessor() {
            @Override
            public MockHttpServletRequest postProcessRequest(final MockHttpServletRequest request) {
                SecurityContextHolder.getContext().setAuthentication(
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()));
                return request;
            }
        };
    }
}
