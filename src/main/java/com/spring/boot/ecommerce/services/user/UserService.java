package com.spring.boot.ecommerce.services.user;

import com.spring.boot.ecommerce.entity.Session;
import com.spring.boot.ecommerce.entity.User;
import com.spring.boot.ecommerce.model.request.user.SignUpRequest;
import com.spring.boot.ecommerce.model.request.user.UpdateUserRequest;
import com.spring.boot.ecommerce.model.response.user.UserDetailResponse;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

public interface UserService {
    Session signUp(SignUpRequest signUpRequest, PasswordEncoder passwordEncoder);

    UserDetailResponse findById(String id);

    User updateProfileUser(String id, UpdateUserRequest userRequest);

    Page<User> getAllUser(int pageNumber, int pageSize);

    List<User> getListUser();

    void save(User user);

    void delete(String id);
}
