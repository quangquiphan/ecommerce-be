package com.spring.boot.ecommerce.controller;

public interface ApiPath {
    String BASE_API = "/api";
    String ID = "/{id}";
    String ALL = "/all";

    //    APIs Authenticated
    String AUTHENTICATE_APIS = BASE_API + "/auth";

    String SIGN_IN = "/sign-in";

    String SIGN_OUT = "/sign-out";

    String INFO_USER = "/info";

    //    APIs User
    String USER_APIs = BASE_API + "/user";
    String SIGN_UP = "/sign-up";

    //    APIs Category
    String CATEGORY_APIs = BASE_API + "/category";

    //    APIs Product
    String PRODUCT_APIs = BASE_API + "/product";

    String PRODUCT_IMAGE_APIs = BASE_API + "/product-image";

    //    APIs Brand
    String BRAND_APIs = BASE_API + "/brand";

    //    APIs Order
    String ORDER_APIs = BASE_API + "/order";

    String CART_APIs = BASE_API + "/cart";

    String DASHBOARD_APIs = BASE_API + "/dashboard";

    String COMMENT_APIs = BASE_API + "/comment";

    String NOTIFICATION_APIs = BASE_API + "/notification";
}
