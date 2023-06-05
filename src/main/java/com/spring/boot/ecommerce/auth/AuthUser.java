package com.spring.boot.ecommerce.auth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spring.boot.ecommerce.common.enums.UserRole;
import com.spring.boot.ecommerce.common.enums.Status;
import com.spring.boot.ecommerce.entity.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Collection;
import java.util.List;

public class AuthUser implements UserDetails  {
    @Getter
    private final String id;
    private final String username;
    private final String password;

    @Getter
    private String firstName;
    @Getter
    private String lastName;
    @Getter
    private String email;
    @Getter
    private String phoneNumber;
    @Getter
    @Enumerated(EnumType.STRING)
    private UserRole userRole;
    @Getter
    private Status status;

    public AuthUser(User user) {

        String firstName = "";
        if (user.getFirstName() != null) {
            firstName = user.getFirstName();
        }
        String lastName = "";
        if (user.getFirstName() != null) {
            lastName = user.getLastName();
        }

        this.id = user.getId();
        this.username = user.getEmail();
        this.password = "";
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = user.getEmail();
        this.phoneNumber = user.getPhoneNumber();
        this.userRole = user.getUserRole();
        this.status = user.getStatus();
    }

    @Override
    @JsonIgnore
    public String getUsername() {
        return username;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(userRole.name()));
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
