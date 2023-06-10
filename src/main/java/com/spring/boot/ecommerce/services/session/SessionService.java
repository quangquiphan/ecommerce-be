package com.spring.boot.ecommerce.services.session;

import com.spring.boot.ecommerce.auth.AuthUser;
import com.spring.boot.ecommerce.entity.Session;
import com.spring.boot.ecommerce.model.request.auth.ChangePassword;
import com.spring.boot.ecommerce.model.request.auth.SignInRequest;
import org.springframework.security.crypto.password.PasswordEncoder;

public interface SessionService {
    Session signIn(SignInRequest signInRequest, PasswordEncoder passwordEncoder, Boolean keepLogin);

    String signOut(String token);

    String changePassword(AuthUser authUser, ChangePassword changePassword, PasswordEncoder passwordEncoder);

    Session findById(String token);

    void delete(String token);
}
