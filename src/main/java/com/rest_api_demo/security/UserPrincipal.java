package com.rest_api_demo.security;

import com.rest_api_demo.domain.RoleType;
import com.rest_api_demo.domain.UserEntity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


@RequiredArgsConstructor
@Getter
@Setter
public class UserPrincipal implements UserDetails {

    private final String email;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;


    public static UserPrincipal create(final UserEntity user) {
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        user.getRoles()
                .forEach(role -> grantedAuthorities.add(new SimpleGrantedAuthority(role.name())));

        return
                new UserPrincipal(
                        user.getId(),
                        user.getPassword(),
                        grantedAuthorities
                );
    }

    public static boolean isNotAdmin(final UserPrincipal userPrincipal){
        return userPrincipal.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .noneMatch(s -> s.equals(RoleType.ROLE_ADMIN.name())||s.equals(RoleType.ROLE_SUPER_ADMIN.name()));
    }


    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

}
