package com.example.team_project.domain.domain.user.service.purchase;

import com.example.team_project.domain.domain.address.domain.UserAddress;
import com.example.team_project.domain.domain.order.item.domain.Order;
import com.example.team_project.domain.domain.product.product.domain.Product;
import com.example.team_project.domain.domain.user.domain.User;

import java.util.List;

public interface UserPurchaseService {
    public void purchaseItem(Long userId, Product product);

    public void cancelPurchaseItem(Long userId, Product product);

    public List<UserAddress> getAddressList(Long userId);

    public List<Order> getPurchaseItemList(Long userId);
}
