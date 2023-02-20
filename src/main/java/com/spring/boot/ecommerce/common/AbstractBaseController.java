package com.spring.boot.ecommerce.common;

import com.spring.boot.ecommerce.common.utils.Constant;
import com.spring.boot.ecommerce.common.utils.ResponseUtil;
import com.spring.boot.ecommerce.config.jwt.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;

public abstract class AbstractBaseController {

    @Autowired
    public ResponseUtil responseUtil;

    @Autowired
    public JwtTokenUtil jwtTokenUtil;

    public final SimpleDateFormat dateFormat = new SimpleDateFormat(Constant.DATE_FORMAT);

}
