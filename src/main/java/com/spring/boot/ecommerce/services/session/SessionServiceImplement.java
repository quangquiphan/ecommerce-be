package com.spring.boot.ecommerce.services.session;

import com.spring.boot.ecommerce.common.enums.Status;
import com.spring.boot.ecommerce.common.exceptions.ApplicationException;
import com.spring.boot.ecommerce.common.utils.*;
import com.spring.boot.ecommerce.config.jwt.JwtTokenUtil;
import com.spring.boot.ecommerce.entity.Session;
import com.spring.boot.ecommerce.entity.User;
import com.spring.boot.ecommerce.model.request.auth.SignInRequest;
import com.spring.boot.ecommerce.repositories.SessionRepository;
import com.spring.boot.ecommerce.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class SessionServiceImplement implements SessionService {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    final UserRepository userRepository;
    final SessionRepository sessionRepository;

    public SessionServiceImplement(UserRepository userRepository, SessionRepository sessionRepository) {
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
    }

    SimpleDateFormat dateFormat = new SimpleDateFormat(Constant.API_FORMAT_DATE);

    @Override
    public Session signIn(SignInRequest signInRequest, PasswordEncoder passwordEncoder, Boolean isKeepLogin) {
        User existUser = userRepository.getByEmailAndStatus(signInRequest.getEmail(), Status.ACTIVE);
        boolean isValidPassword = checkPassword(
                signInRequest.getPasswordHash().trim().concat(existUser.getPasswordSalt().trim()),
                existUser.getPasswordHash(), passwordEncoder
        );

        List<Session> sessions = sessionRepository.findAllByUserId(existUser.getId());
        sessionRepository.deleteAll(sessions);

        Validator.notNullAndNotEmpty(existUser, RestAPIStatus.NOT_FOUND, "User is not found");

        if (!isValidPassword) {
            throw new ApplicationException(RestAPIStatus.BAD_REQUEST, "Password invalid");
        }

        Session session = new Session();
        session.setAccessToken(jwtTokenUtil.generateAccessToken(existUser));
        session.setUserId(existUser.getId());
        session.setCreatedDate(DateUtil.convertToUTC(new Date()));

        if (isKeepLogin) {
            try {
                session.setExpiryDate(dateFormat.parse("12/31/9999 00:00:00"));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            session.setExpiryDate(DateUtil.addHoursToJavaUtilDate(new Date(), 24));
        }

        sessionRepository.save(session);
        return session;
    }

    @Override
    public String signOut(String token) {
        Session session = sessionRepository.getByAccessToken(token);
        if (session != null) {
            sessionRepository.delete(session);
        }
        return "Sign out successfully!";
    }

    @Override
    public Session findById(String token) {
        return sessionRepository.getById(token);
    }

    @Override
    public void delete(String token) {
        Session session = sessionRepository.getById(token);
        sessionRepository.delete(session);
    }

    private Boolean checkPassword(String passwordRequest, String password, PasswordEncoder passwordEncoder) {
        return passwordEncoder.matches(passwordRequest, password);
    }
}
