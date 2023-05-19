package com.spring.boot.ecommerce.model.request.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.spring.boot.ecommerce.common.utils.ParamError;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateUserRequest {
    @NotBlank(message = ParamError.FIELD_NAME)
    @Size(message = ParamError.MAX_LENGTH)
    private String firstName;

    @NotBlank(message = ParamError.FIELD_NAME)
    @Size(message = ParamError.MAX_LENGTH)
    private String lastName;

//    @NotBlank(message = ParamError.)
    @Size(message = ParamError.MAX_LENGTH)
    private String phoneNumber;

    private String email;

    private String country;

    private String address;
}
