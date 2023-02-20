package com.spring.boot.ecommerce.services.session;

import com.spring.boot.ecommerce.entity.Session;
import com.spring.boot.ecommerce.model.request.auth.SignInRequest;
import org.springframework.security.crypto.password.PasswordEncoder;

public interface SessionService {
    Session signIn(SignInRequest signInRequest, PasswordEncoder passwordEncoder, Boolean keepLogin);

    String signOut(String token);

    Session findById(String token);

    void delete(String token);
}
