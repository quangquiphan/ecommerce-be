package com.spring.boot.ecommerce.model.request.auth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.spring.boot.ecommerce.common.utils.ParamError;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SignInRequest {

    @JsonProperty(value = "username")
    @NotBlank(message = ParamError.FIELD_NAME)
    public String username;

    @JsonProperty(value = "password_hash")
    @NotBlank(message = ParamError.FIELD_NAME)
    public String passwordHash;

    @JsonProperty(value = "is_keep_login")
    boolean isKeepLogin;
}
