package com.example.team_project.domain.domain.user.service.purchase;

import com.example.team_project.domain.domain.product.product.domain.Product;

public interface UserPurchaseCancelService {
    void cancelPurchase(Long id, Product product);
}
