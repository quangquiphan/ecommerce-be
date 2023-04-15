package com.spring.boot.ecommerce.controller;

import com.spring.boot.ecommerce.auth.AuthUser;
import com.spring.boot.ecommerce.auth.AuthorizeValidator;
import com.spring.boot.ecommerce.common.AbstractBaseController;
import com.spring.boot.ecommerce.common.enums.UserRole;
import com.spring.boot.ecommerce.common.exceptions.ApplicationException;
import com.spring.boot.ecommerce.common.utils.Constant;
import com.spring.boot.ecommerce.common.utils.RestAPIResponse;
import com.spring.boot.ecommerce.common.utils.RestAPIStatus;
import com.spring.boot.ecommerce.entity.User;
import com.spring.boot.ecommerce.model.response.PagingResponse;
import com.spring.boot.ecommerce.model.response.user.UserDetailResponse;
import com.spring.boot.ecommerce.model.request.user.SignUpRequest;
import com.spring.boot.ecommerce.model.request.user.UpdateUserRequest;
import com.spring.boot.ecommerce.services.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping(ApiPath.USER_APIs)
public class UserController extends AbstractBaseController {
    final PasswordEncoder passwordEncoder;
    final UserService userService;

    public UserController(PasswordEncoder passwordEncoder, UserService userService) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    @Operation(summary = "signUp")
    @RequestMapping(path = ApiPath.SIGN_UP, method = RequestMethod.POST)
    public ResponseEntity<RestAPIResponse> signUp(
            @RequestBody @Valid SignUpRequest signUpRequest
    ) {
        User user = userService.signUp(signUpRequest, passwordEncoder);
        return responseUtil.successResponse(user);
    }

    @Operation(summary = "updateProfileUser")
    @RequestMapping(path = ApiPath.ID, method = RequestMethod.PUT)
    public ResponseEntity<RestAPIResponse> updateProfileUser(
            @RequestParam String id,
            @RequestBody UpdateUserRequest userRequest
            ){
        User user = userService.updateProfileUser(id, userRequest);
        if (user == null) {
            throw new ApplicationException(RestAPIStatus.NOT_FOUND);
        }
        return responseUtil.successResponse(
                new UserDetailResponse(
                        user.getFirstName(),
                        user.getLastName(),
                        user.getUsername(),
                        user.getPhoneNumber(),
                        user.getEmail(),
                        user.getCountry(),
                        user.getStatus(),
                        user.getUserRole()
                )
        );
    }

    @Operation(summary = "getProfileUser")
    @RequestMapping(path = ApiPath.ID, method = RequestMethod.GET)
    public ResponseEntity<RestAPIResponse> getProfileUser(
            @RequestParam String id
    ){
        UserDetailResponse user = userService.findById(id);
        if (user == null) {
            throw new ApplicationException(RestAPIStatus.NOT_FOUND);
        }
        return responseUtil.successResponse(user);
    }

    @AuthorizeValidator(UserRole.ADMIN)
    @RequestMapping(path = ApiPath.ALL, method = RequestMethod.GET)
    @Operation(summary = "getAllUser")
    public ResponseEntity<RestAPIResponse> getAllUser(
            @RequestParam(defaultValue = "1") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        return responseUtil.successResponse(
                new PagingResponse(userService.getAllUser(pageNumber, pageSize))
        );
    }

    @AuthorizeValidator(UserRole.ADMIN)
    @RequestMapping(path = ApiPath.ID, method = RequestMethod.DELETE)
    @Operation(summary = "deleteUser")
    public ResponseEntity<RestAPIResponse> deleteUser(
            @PathVariable String id
    ) {
        userService.delete(id);
        return responseUtil.successResponse("Delete successfully!");
    }
}
