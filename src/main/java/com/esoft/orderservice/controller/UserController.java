package com.esoft.orderservice.controller;


import com.esoft.orderservice.helper.jwt.JwtTokenProvider;
import com.esoft.orderservice.helper.payload.LoginRequest;
import com.esoft.orderservice.helper.payload.LoginResponse;
import com.esoft.orderservice.model.CustomUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @PostMapping("/token")
    public LoginResponse authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        logger.error("authenticateUser>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        Authentication authentication = null;
        try {
            // Xác thực từ username và password.
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );
        }catch (Exception ex){
            ex.printStackTrace();
        }
        // Nếu không xảy ra exception tức là thông tin hợp lệ
        // Set thông tin authentication vào Security Context
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Trả về jwt cho người dùng.
        String jwt = tokenProvider.generateToken((CustomUserDetails) authentication.getPrincipal());
        return new LoginResponse(jwt);
    }

    @GetMapping("/helloworld")
    public String helloworld(){
        return new String("helloworld");
    }

}