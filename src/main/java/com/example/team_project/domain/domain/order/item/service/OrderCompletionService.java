package com.example.team_project.domain.domain.order.item.service;


public interface OrderCompletionService {
    void processOrderPayment(Long userId, Long userAddressId, Long paymentId, Long orderListId);

}
