package com.rest_api_demo.service.impl;


import com.rest_api_demo.exceptions.ResourceNotFoundException;
import com.rest_api_demo.exceptions.ServiceException;
import com.rest_api_demo.domain.RoleType;
import com.rest_api_demo.domain.UserEntity;
import com.rest_api_demo.repository.UserRepository;
import com.rest_api_demo.service.AbstractService;
import com.rest_api_demo.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Set;


@Service
public class UserServiceImpl extends AbstractService<UserEntity, String> implements UserService {

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        super(userRepository);
        this.userRepository =userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public UserEntity save(UserEntity obj) {
        validateEmailAvailability(obj.getEmail());
        encodePassword(obj);
        obj.setRoles(Set.of(RoleType.ROLE_USER));
        return super.save(obj);
    }

    @Override
    public UserEntity update(UserEntity obj, String id) {
        UserEntity entity= userRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("not found"));
        String newPassword = obj.getPassword();
        if (newPassword!=null) {
            entity.setPassword(newPassword);
            encodePassword(entity);
        }
        Set<RoleType> roles=obj.getRoles();
        if (roles!=null){
            entity.setRoles(roles);
        }
        return userRepository.save(entity);
    }


    private void encodePassword(final UserEntity user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
    }

    private void validateEmailAvailability(final String email) {
        if (userRepository.existsById(email)) {
            throw new ServiceException(HttpStatus.CONFLICT.value(), "User already exists");
        }
    }
}
