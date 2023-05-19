package com.spring.boot.ecommerce.model.response.user;

import com.spring.boot.ecommerce.common.enums.UserRole;
import com.spring.boot.ecommerce.common.enums.Status;
import com.spring.boot.ecommerce.entity.User;
import com.spring.boot.ecommerce.model.response.cart.CartResponse;
import lombok.Data;

import java.util.Date;
import java.util.List;

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
    private String address;
    private Status status;
    private UserRole userRole;
    private Date createdDate;
    private Date updatedDate;
    private List<CartResponse> cartItems;

    public UserDetailResponse(
            User user
    ) {
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.username = user.getUsername();
        this.avatarName = getAvatarName(firstName, lastName);
        this.fullName = getFullName(firstName, lastName);
        this.phoneNumber = user.getPhoneNumber();
        this.email = user.getEmail();
        this.country = user.getCountry();
        this.address = user.getAddress();
        this.status = user.getStatus();
        this.userRole = user.getUserRole();
    }

    public UserDetailResponse(
            User user,
            List<CartResponse> cartItems
    ) {
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.username = user.getUsername();
        this.avatarName = getAvatarName(user.getFirstName(), user.getLastName());
        this.fullName = getFullName(user.getFirstName(), user.getLastName());
        this.phoneNumber = user.getPhoneNumber();
        this.email = user.getEmail();
        this.country = user.getCountry();
        this.address = user.getAddress();
        this.status = user.getStatus();
        this.userRole = user.getUserRole();
        this.createdDate = user.getCreatedDate();
        this.updatedDate = user.getUpdatedDate();
        this.cartItems = cartItems;
    }

    private String getAvatarName(String firstName, String lastName) {
        if (firstName.isEmpty() || firstName == null)
            return firstName = "";

        if (lastName.isEmpty() || lastName == null)
            return lastName = "";

        return String.valueOf(firstName.toUpperCase().charAt(0))
                .concat(String.valueOf(lastName.toUpperCase().charAt(0)));
    }

    private String getFullName(String firstName, String lastName) {
        if ((firstName.isEmpty() || firstName == null) && (lastName.isEmpty() || lastName == null)){
            return "";
        }

        return String.format("%s %s", firstName, lastName);
    }
}
