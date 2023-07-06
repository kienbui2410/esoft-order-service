package com.esoft.orderservice.controller;

import com.esoft.orderservice.common.DateUtil;
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
public class ReportControllerTest {

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
    public void reportController_whenCreateOrder_thenReturnNumberOfOrder() throws Exception {

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
        orderService.create(mockOrder, userDetails.getUser());
        orderService.create(mockOrder2, userDetails.getUser());
        orderService.create(mockOrder3, userDetails.getUser());

        ResultActions responseBody = this.mockMvc.perform(MockMvcRequestBuilders.get("/reports/users/2/order-number")
                .with(sessionUser(userDetails))
                .content(asJsonString(mockOrder))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        ResponseMessage response = this.mapper.readValue(responseBody.andReturn().getResponse().getContentAsString(), ResponseMessage.class);
        assertTrue(((Integer)response.getData()) == 3);
    }

    @Test
    public void reportController_whenCreateOrder_thenReturnRevenue() throws Exception {

        Order mockOrder = Order.builder()
                .category(Order.Category.SUPER_LUXURY)
                .quantity(1L)
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
                .price(new BigDecimal("200000"))
                .build();
        Order mockOrder3 = Order.builder()
                .category(Order.Category.SUPER_LUXURY)
                .quantity(3L)
                .serviceName(Order.ServiceName.VIDEO_EDITING)
                .desc("unit test 3")
                .note("unit test 3")
                .price(new BigDecimal("300000"))
                .build();
        orderService.create(mockOrder, userDetails.getUser());
        orderService.create(mockOrder2, userDetails.getUser());
        orderService.create(mockOrder3, userDetails.getUser());

        ResultActions responseBody = this.mockMvc.perform(MockMvcRequestBuilders.get("/reports/users/2/revenue")
                .with(sessionUser(userDetails))
                .content(asJsonString(mockOrder))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        ResponseMessage response = this.mapper.readValue(responseBody.andReturn().getResponse().getContentAsString(), ResponseMessage.class);
        //1*100000 + 2*200000 + 3*300000 = 1400000
        assertTrue(((Integer)response.getData()) == 1400000);
    }

    @Test
    public void reportController_whenCreateOrder_thenReturnInfoByTime() throws Exception {

        Order mockOrder = Order.builder()
                .category(Order.Category.SUPER_LUXURY)
                .quantity(1L)
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
                .price(new BigDecimal("200000"))
                .build();
        Order mockOrder3 = Order.builder()
                .category(Order.Category.SUPER_LUXURY)
                .quantity(3L)
                .serviceName(Order.ServiceName.VIDEO_EDITING)
                .desc("unit test 3")
                .note("unit test 3")
                .price(new BigDecimal("300000"))
                .build();
        orderService.create(mockOrder, userDetails.getUser());
        orderService.create(mockOrder2, userDetails.getUser());
        orderService.create(mockOrder3, userDetails.getUser());

        ResultActions responseBody = this.mockMvc.perform(MockMvcRequestBuilders.get("/reports/order-revenue-summary/year/2023/month/7")
                .with(sessionUser(userDetails))
                .content(asJsonString(mockOrder))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        ResponseMessage response = this.mapper.readValue(responseBody.andReturn().getResponse().getContentAsString(), ResponseMessage.class);
        LinkedHashMap<String,Object> data = (LinkedHashMap<String,Object>)response.getData();
        //1*100000 + 2*200000 + 3*300000 = 1400000
        assertTrue(((Integer)data.get("noOfOrder")) == 3);
        assertTrue(((Integer)data.get("revenue")) == 1400000);
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
