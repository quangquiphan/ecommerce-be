package com.spring.boot.ecommerce.controller;

import com.spring.boot.ecommerce.auth.AuthUser;
import com.spring.boot.ecommerce.common.AbstractBaseController;
import com.spring.boot.ecommerce.common.exceptions.ApplicationException;
import com.spring.boot.ecommerce.common.utils.Constant;
import com.spring.boot.ecommerce.common.utils.RestAPIResponse;
import com.spring.boot.ecommerce.common.utils.RestAPIStatus;
import com.spring.boot.ecommerce.entity.Session;
import com.spring.boot.ecommerce.entity.User;
import com.spring.boot.ecommerce.model.response.auth.SignInResponse;
import com.spring.boot.ecommerce.model.request.auth.SignInRequest;
import com.spring.boot.ecommerce.services.session.SessionService;
import com.spring.boot.ecommerce.services.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping(ApiPath.AUTHENTICATE_APIS)
public class AuthenticatedController extends AbstractBaseController {
    final PasswordEncoder passwordEncoder;
    final UserService userService;
    final SessionService sessionService;

    public AuthenticatedController(
            PasswordEncoder passwordEncoder,
            UserService userService,
            SessionService sessionService) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.sessionService = sessionService;
    }

    @Operation(summary = "signIn")
    @RequestMapping(path = ApiPath.SIGN_IN, method = RequestMethod.POST)
    public ResponseEntity<RestAPIResponse> signIn(
            @RequestBody @Valid SignInRequest signInRequest
    ) {
        Session session = sessionService.signIn(signInRequest, passwordEncoder, signInRequest.isKeepLogin());
        User user = userService.findById(session.getUserId());
        return responseUtil.successResponse(
                new SignInResponse(
                        session.getId(),
                        session.getExpiryDate().getTime(),
                        user.getUserRole(),
                        user.getStatus()));
    }

    @Operation(summary = "signOut")
    @RequestMapping(path = ApiPath.SIGN_OUT, method = RequestMethod.DELETE)
    public ResponseEntity<RestAPIResponse> signOut(
            HttpServletRequest request
    ) {
        if (request.getHeader(Constant.HEADER_TOKEN) == null) {
            throw new ApplicationException(RestAPIStatus.NOT_FOUND);
        }
        return responseUtil.successResponse(sessionService.signOut(request.getHeader(Constant.HEADER_TOKEN)));
    }

    @Operation(summary = "getInfo")
    @RequestMapping(value = ApiPath.INFO_USER, method = RequestMethod.GET)
    public ResponseEntity<RestAPIResponse> getInfo(
            HttpServletRequest servletRequest
    ){
        AuthUser user = jwtTokenUtil.getUserIdFromJWT(servletRequest.getHeader(Constant.HEADER_TOKEN));

        return responseUtil.successResponse(user);
    }
}
