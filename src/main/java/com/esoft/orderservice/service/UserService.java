package com.esoft.orderservice.service;

import com.esoft.orderservice.model.CustomUserDetails;
import com.esoft.orderservice.model.User;
import com.esoft.orderservice.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) {

        User user = userRepo.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new CustomUserDetails(user);
    }

    public UserDetails loadUserById(Long userId) {

        User user = userRepo.findById(userId).get();
        if (user == null) {
            throw new UsernameNotFoundException(String.valueOf(userId));
        }
        return new CustomUserDetails(user);
    }


}