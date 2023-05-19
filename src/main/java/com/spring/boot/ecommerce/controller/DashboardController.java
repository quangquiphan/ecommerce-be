package com.spring.boot.ecommerce.controller;

import com.spring.boot.ecommerce.common.AbstractBaseController;
import com.spring.boot.ecommerce.common.utils.RestAPIResponse;
import com.spring.boot.ecommerce.services.dashboard.DashboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiPath.BASE_API)
public class DashboardController extends AbstractBaseController {
    final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @RequestMapping(path = "/dashboard",method = RequestMethod.GET)
    public ResponseEntity<RestAPIResponse> dashboard(){
        return responseUtil.successResponse(dashboardService.dashboard());
    }

    @RequestMapping(path = "/chart",method = RequestMethod.GET)
    public ResponseEntity<RestAPIResponse> chart(){
        return responseUtil.successResponse(dashboardService.chartByMonthAndYear());
    }
}
