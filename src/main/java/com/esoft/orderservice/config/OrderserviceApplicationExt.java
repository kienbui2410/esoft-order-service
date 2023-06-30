package com.esoft.orderservice.config;

import com.esoft.orderservice.model.User;
import com.esoft.orderservice.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class OrderserviceApplicationExt implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(OrderserviceApplicationExt.class, args);
    }

    @Autowired
    UserRepo userRepo;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Khi chương trình chạy
        // Insert vào csdl một user.
        User user = new User();
        user.setUsername("loda");
        user.setPassword(passwordEncoder.encode("loda"));
        userRepo.save(user);
        System.out.println(user);
    }
}

