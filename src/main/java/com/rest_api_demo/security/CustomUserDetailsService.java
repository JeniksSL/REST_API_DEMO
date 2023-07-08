package com.rest_api_demo.security;



import com.rest_api_demo.domain.RoleType;
import com.rest_api_demo.domain.UserEntity;
import com.rest_api_demo.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final String testUserName = "testUser@Name.com";

    @Override
    public UserPrincipal loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user;

        if (username.equals(testUserName)) {//Custom user for test perform
            user=UserEntity
                    .builder()
                    .email(testUserName)
                    .password(passwordEncoder.encode(testUserName))
                    .roles(Arrays.stream(RoleType.values()).collect(Collectors.toSet()))
                    .build();
        } else user=userRepository.findById(username).orElseThrow(()->new UsernameNotFoundException(username));

        return UserPrincipal.create(user);
    }
}