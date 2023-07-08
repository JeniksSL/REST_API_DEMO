package com.food_manager.config;


import com.food_manager.data.model.service_model.User;
import com.food_manager.data.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService  implements UserDetailsService {//


    private final UserService userService;

    public CustomUserDetailsService(UserService userService) {
        this.userService = userService;

    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user=userService.findById(username);
        System.out.println(user);
        if (user==null) throw new UsernameNotFoundException(username);
        return user;
    }
}