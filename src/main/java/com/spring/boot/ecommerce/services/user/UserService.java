package com.spring.boot.ecommerce.services.user;

import com.spring.boot.ecommerce.auth.AuthUser;
import com.spring.boot.ecommerce.entity.User;
import com.spring.boot.ecommerce.model.request.user.SignUpRequest;
import com.spring.boot.ecommerce.model.request.user.UpdateUserRequest;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;

public interface UserService {
    User signUp(SignUpRequest signUpRequest, PasswordEncoder passwordEncoder);

    User findById(String id);

    User updateProfileUser(String id, UpdateUserRequest userRequest);

    Page<User> getAllUser(int pageNumber, int pageSize);

    void save(User user);

    void delete(String id);
}
