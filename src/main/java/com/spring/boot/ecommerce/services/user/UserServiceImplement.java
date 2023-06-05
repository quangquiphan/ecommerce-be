package com.spring.boot.ecommerce.services.user;

import com.spring.boot.ecommerce.common.enums.UserRole;
import com.spring.boot.ecommerce.common.enums.Status;
import com.spring.boot.ecommerce.common.exceptions.ApplicationException;
import com.spring.boot.ecommerce.common.utils.*;
import com.spring.boot.ecommerce.config.jwt.JwtTokenUtil;
import com.spring.boot.ecommerce.entity.Session;
import com.spring.boot.ecommerce.entity.User;
import com.spring.boot.ecommerce.model.request.user.SignUpRequest;
import com.spring.boot.ecommerce.model.request.user.UpdateUserRequest;
import com.spring.boot.ecommerce.model.response.user.UserDetailResponse;
import com.spring.boot.ecommerce.repositories.CartRepository;
import com.spring.boot.ecommerce.repositories.SessionRepository;
import com.spring.boot.ecommerce.repositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImplement implements UserService {
    final UserRepository userRepository;
    final CartRepository cartRepository;
    final JwtTokenUtil jwtTokenUtil;
    final SessionRepository sessionRepository;

    public UserServiceImplement(UserRepository userRepository, CartRepository cartRepository, JwtTokenUtil jwtTokenUtil, SessionRepository sessionRepository) {
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
        this.jwtTokenUtil = jwtTokenUtil;
        this.sessionRepository = sessionRepository;
    }

    SimpleDateFormat dateFormat = new SimpleDateFormat();

    @Override
    public Session signUp(SignUpRequest signUpRequest, PasswordEncoder passwordEncoder) {
        User existUser = userRepository.getByEmailAndStatus(signUpRequest.getEmail(), Status.ACTIVE);
        Validator.mustNull(existUser, RestAPIStatus.EXISTED, "User already existed");
        boolean isPassword = checkPassword(signUpRequest.getPasswordHash(), signUpRequest.getConfirmPassword());

        if (!isPassword) {
            throw new ApplicationException(RestAPIStatus.BAD_REQUEST, "Confirm password is not the same as the password");
        }

        String passwordSalt = AppUtil.generateSalt();

        User user = new User();
        user.setId(UniqueID.getUUID());
        user.setEmail(signUpRequest.getEmail());
        user.setPasswordSalt(passwordSalt);
        user.setPasswordHash(setPasswordHash(passwordEncoder, signUpRequest.getPasswordHash(), passwordSalt));
        user.setStatus(Status.ACTIVE);
        user.setUserRole(signUpRequest.getRole());
        userRepository.save(user);

        Session session = new Session();
        session.setAccessToken(jwtTokenUtil.generateAccessToken(user));
        session.setUserId(existUser.getId());
        session.setCreatedDate(DateUtil.convertToUTC(new Date()));
        session.setExpiryDate(DateUtil.addHoursToJavaUtilDate(new Date(), 24));

        return sessionRepository.save(session);
    }

    @Override
    public UserDetailResponse findById(String id) {
        User user = userRepository.getById(id);
        return new UserDetailResponse(user, cartRepository.findCartByUserId(id));
    }

    @Override
    public User updateProfileUser(String id, UpdateUserRequest userRequest) {
        User user = userRepository.getById(id);

        if (user == null) {
            throw new ApplicationException(RestAPIStatus.NOT_FOUND, "User is not found");
        }

        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setPhoneNumber(userRequest.getPhoneNumber());
        user.setEmail(userRequest.getEmail());
        user.setAddress(userRequest.getAddress());

        userRepository.save(user);
        return user;
    }

    @Override
    public Page<User> getAllUser(int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize);
        System.out.println(userRepository.findAll(pageRequest));
        return userRepository.findAll(pageRequest);
    }

    @Override
    public List<User> getListUser() {
        return userRepository.findAll();
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public void delete(String id) {
        User user = userRepository.getById(id);

        if (user == null) {
            throw new ApplicationException(RestAPIStatus.NOT_FOUND);
        }

        if (user.getUserRole().equals(UserRole.ADMIN)) {
            throw new ApplicationException(RestAPIStatus.UNAUTHORIZED);
        }

        if (user.getStatus().equals(Status.IN_ACTIVE)) {
            userRepository.delete(user);
            return;
        }

        user.setStatus(Status.IN_ACTIVE);
        userRepository.save(user);
    }

    private String setPasswordHash(PasswordEncoder passwordEncoder, String password, String passwordSalt) {
        return passwordEncoder.encode(password.trim().concat(passwordSalt));
    }

    private Boolean checkPassword(String passwordHash, String confirmPasswordHash) {
        return Objects.equals(passwordHash, confirmPasswordHash);
    }
}
