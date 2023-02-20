package com.spring.boot.ecommerce.model.response.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.spring.boot.ecommerce.common.enums.UserRole;
import com.spring.boot.ecommerce.common.enums.Status;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@Setter
public class SignInResponse {
    @JsonProperty(value = "access_token")
    private String accessToken;

    @JsonProperty(value = "expire_time")
    private Long expireTime;

    private UserRole userRole;

    @Enumerated(EnumType.STRING)
    private Status status;

    public SignInResponse(String accessToken, Long expireTime, UserRole userRole, Status status) {
        this.accessToken = accessToken;
        this.expireTime = expireTime;
        this.userRole = userRole;
        this.status = status;
    }
}
