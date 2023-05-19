package com.spring.boot.ecommerce.services.dashboard;

import com.spring.boot.ecommerce.model.response.dashboard.Chart;
import com.spring.boot.ecommerce.model.response.dashboard.Dashboard;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DashboardService {
    Dashboard dashboard();

    List<Chart> chartByMonthAndYear();
}
