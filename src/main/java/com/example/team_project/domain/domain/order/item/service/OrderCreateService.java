package com.example.team_project.domain.domain.order.item.service;

import com.example.team_project.domain.domain.order.item.domain.Order;
import com.example.team_project.exception.OutOfStockException;
import com.example.team_project.exception.ProductNotFoundException;
import com.example.team_project.exception.UserNotFoundException;


public interface OrderCreateService {

    /**
     * User와 OrderList 고유 ID, OrderToProduct(주문상품 구입개수,상태 및 가격 등)를 가지고 생성하는데
     * 사용자와 상품을 검증해준뒤 오더리스트 존재하지 않을시 OrderList를 생성을 한뒤 Order를 생성후 레퍼지토리에 저장
     * 유저와 상품 검증단계에서는 해당 고유 ID로 레퍼지토리에서 찾을 수 없을시 각각 NotFoundException 발생
     * 상품 재고 부족시 상품 소진 알리는 Exception 발생
     *
     * @param userId        : 사용자 고유 ID
     * @param productId     : 상품 고유 ID
     * @param quantity      : 주문 상품 개수
     * @throws UserNotFoundException    : 해당 사용자를 찾을 수 없을 때
     * @throws ProductNotFoundException :해당 상품을 찾을 수 없을 때
     * @throws OutOfStockException      : 주문하려는 상품의 재고가 떨어졌거나 부족할 때
     **/
    void create(Long userId, Long productId, int quantity, Long couponId);
}
