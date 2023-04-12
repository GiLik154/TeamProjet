package com.example.team_project.domain.domain.order.item.service;

import com.example.team_project.exception.OrderNotFoundException;

public interface OrderCancelService {

    /**
     * OrderId를 통해 레퍼지토리에서 존재하는 주문인지 확인 존재하면 true 없으면 false 리턴
     * false를 리턴 받을시 OrderNotFoundException 발생
     * true를 리턴 받을시 정상적으로 취소가 되어 주문상품 상태가 ENUM을 통해  CANCELED 로 바뀜
     *
     * @param orderToProductId: 주문상품 상태 클래스의 고유 ID
     * @param orderId           : 주문 클래스의 고유 ID
     * @throws OrderNotFoundException: 해당 주문을 찾을 수 없을 때
     **/
    void cancel(Long orderToProductId, Long orderId);
}
