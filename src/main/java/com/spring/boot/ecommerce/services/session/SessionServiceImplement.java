package com.spring.boot.ecommerce.services.session;

import com.spring.boot.ecommerce.auth.AuthUser;
import com.spring.boot.ecommerce.common.enums.Status;
import com.spring.boot.ecommerce.common.exceptions.ApplicationException;
import com.spring.boot.ecommerce.common.utils.*;
import com.spring.boot.ecommerce.config.jwt.JwtTokenUtil;
import com.spring.boot.ecommerce.entity.Session;
import com.spring.boot.ecommerce.entity.User;
import com.spring.boot.ecommerce.model.request.auth.ChangePassword;
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
import java.util.Objects;

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
    public String changePassword(AuthUser authUser, ChangePassword changePassword, PasswordEncoder passwordEncoder) {
        User user = userRepository.getById(authUser.getId());

        boolean checkOldPassword = checkPassword(changePassword.getOldPassword().trim().concat(user.getPasswordSalt().trim()),
                user.getPasswordHash(), passwordEncoder);

        if (checkOldPassword == false) {
            throw new ApplicationException(RestAPIStatus.BAD_REQUEST, "Change password failed!");
        }

        boolean checkConfirmPassword = checkConfirmPassword(changePassword.getNewPasswordHash(), changePassword.getConfirmPasswordHash());

        if (checkConfirmPassword == false) {
            throw new ApplicationException(RestAPIStatus.BAD_REQUEST, "Confirm password not matches with password");
        }

        user.setPasswordHash(setPasswordHash(passwordEncoder, changePassword.getNewPasswordHash(), user.getPasswordSalt()));

        userRepository.save(user);
        return "Successfully!";
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

    private String setPasswordHash(PasswordEncoder passwordEncoder, String password, String passwordSalt) {
        return passwordEncoder.encode(password.trim().concat(passwordSalt));
    }

    private Boolean checkConfirmPassword(String passwordHash, String confirmPasswordHash) {
        return Objects.equals(passwordHash, confirmPasswordHash);
    }
}
