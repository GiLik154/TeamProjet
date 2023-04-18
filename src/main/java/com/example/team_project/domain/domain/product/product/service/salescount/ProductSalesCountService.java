package com.example.team_project.domain.domain.product.product.service.salescount;

import com.example.team_project.domain.domain.order.item.domain.Order;
import com.example.team_project.domain.domain.order.item.domain.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductSalesCountService {
    private final OrderRepository orderRepository;

    public void updateSalesCount() {

    }

}
