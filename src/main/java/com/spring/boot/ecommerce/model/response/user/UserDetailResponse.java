package com.spring.boot.ecommerce.model.response.user;

import com.spring.boot.ecommerce.common.enums.UserRole;
import com.spring.boot.ecommerce.common.enums.Status;
import lombok.Data;

@Data
public class UserDetailResponse {
    private String firstName;
    private String lastName;
    private String username;
    private String avatarName;
    private String fullName;
    private String phoneNumber;
    private String email;
    private String country;
    private Status status;
    private UserRole userRole;

    public UserDetailResponse(
            String firstName,
            String lastName,
            String username,
            String phoneNumber,
            String email,
            String country,
            Status status,
            UserRole userRole
    ) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.avatarName = getAvatarName(firstName, lastName);
        this.fullName = getFullName(firstName, lastName);
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.country = country;
        this.status = status;
        this.userRole = userRole;
    }

    private String getAvatarName(String firstName, String lastName) {
        return String.valueOf(firstName.toUpperCase().charAt(0))
                .concat(String.valueOf(lastName.toUpperCase().charAt(0)));
    }

    private String getFullName(String firstName, String lastName) {
        return String.format("%s %s", firstName, lastName);
    }
}
