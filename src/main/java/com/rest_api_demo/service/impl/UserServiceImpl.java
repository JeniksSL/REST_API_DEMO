package com.rest_api_demo.service.impl;


import com.rest_api_demo.exceptions.ResourceNotFoundException;
import com.rest_api_demo.exceptions.ServiceException;
import com.rest_api_demo.domain.RoleType;
import com.rest_api_demo.domain.UserEntity;
import com.rest_api_demo.dto.UserCompact;
import com.rest_api_demo.dto.UserDto;
import com.rest_api_demo.dto.mapper.core.TripleMapper;
import com.rest_api_demo.repository.UserRepository;
import com.rest_api_demo.security.UserPrincipal;
import com.rest_api_demo.service.AbstractService;
import com.rest_api_demo.service.UserService;
import com.rest_api_demo.service.core.PageDto;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl extends AbstractService<UserEntity, UserDto, String> implements UserService {

    public UserServiceImpl(UserRepository userRepository, TripleMapper<UserEntity, UserDto, UserCompact> userMapper, PasswordEncoder passwordEncoder) {
        super(userRepository, userMapper);
        this.userMapper = userMapper;
        this.userRepository =userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    private final TripleMapper<UserEntity, UserDto, UserCompact> userMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public PageDto<UserDto> findAllByEmail(String email, Integer page, Integer size) {
        return (email==null)? findAll(page,size):
                super.findAll(Specification
                .where((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder
                        .like(root.get("id"), "%" + email + "%")),page, size);
    }

    @Override
    public PageDto<UserDto> findAll(Integer page, Integer size) {
        return super.findAll(page, size);
    }

    @Override
    public UserDto findById(String s) {
        return super.findById(s);
    }

    @Override
    public UserDto save(UserDto obj) {
        return super.save(obj);
    }

    @Override
    public UserDto save(UserCompact userCompact) {
        validateEmailAvailability(userCompact.getEmail());
        UserEntity user = userMapper.toEntityFromCompact(userCompact);
        encodePassword(user);
        user.setRoles(Set.of(RoleType.ROLE_USER));
        return userMapper.toDto(userRepository.save(user)); }


    @Override
    public void deleteById(String s) {
        super.deleteById(s);
    }

    @Override
    public UserDto update(UserDto userDto, String s) {
        throw new ServiceException(HttpStatus.NOT_IMPLEMENTED.value(), "Not implemented");
    }

    @Override
    public UserDto updateRoles(Set<String> roles, String userId) {
        if (roles==null) roles=Set.of();
        validateRoles(roles);
        UserDto userDto = super.findById(userId);
        userDto.setRoles(roles);
        return super.update(userDto, userId);
    }

    @Override
    public UserDto updatePassword(UserCompact userCompact, UserPrincipal userPrincipal) {
        UserEntity user = userRepository.findById(userPrincipal.getEmail()).orElseThrow(()->new ResourceNotFoundException("not found"));
        if (userCompact.getEmail().equals(user.getId())) {
            user.setPassword(userCompact.getPassword());
            encodePassword(user);
            return userMapper.toDto(userRepository.save(user));

        } else
            throw new ServiceException(HttpStatus.BAD_REQUEST.value(), "User's email is not same");
    }

    private void encodePassword(final UserEntity user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
    }

    private void validateEmailAvailability(final String email) {
        if (userRepository.existsById(email)) {
            throw new ServiceException(HttpStatus.CONFLICT.value(), "User already exists");
        }
    }
    private void validateRoles(final Set<String> roles){
        if(!Arrays.stream(RoleType.values()).map(Enum::name).collect(Collectors.toSet()).containsAll(roles))
            throw new ServiceException(HttpStatus.BAD_REQUEST.value(), "No such role");
    }



}
