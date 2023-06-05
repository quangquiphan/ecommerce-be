package com.spring.boot.ecommerce.services.dashboard;

import com.spring.boot.ecommerce.model.response.dashboard.Chart;
import com.spring.boot.ecommerce.model.response.dashboard.Dashboard;
import com.spring.boot.ecommerce.repositories.OrderRepository;
import com.spring.boot.ecommerce.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DashBoardServiceImplement implements DashboardService{

    final UserRepository userRepository;

    final OrderRepository orderRepository;

    public DashBoardServiceImplement(
            UserRepository userRepository,
            OrderRepository orderRepository) {
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public Dashboard dashboard() {
        int totalUser = userRepository.countById();
        int totalOrder = orderRepository.countById();
        double totalPrice = orderRepository.queryTopByTotal();

        Dashboard dashboard = new Dashboard();
        dashboard.setTotalUser(totalUser);
        dashboard.setTotalOrder(totalOrder);
        dashboard.setTotalPrice(totalPrice);

        return dashboard;
    }

    @Override
    public List<Chart> chartByMonthAndYear() {
        return orderRepository.countByCreatedDate_YearAndCreatedDate_Month();
    }
}
