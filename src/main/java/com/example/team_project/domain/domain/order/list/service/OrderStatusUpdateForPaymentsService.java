package com.example.team_project.domain.domain.order.list.service;

public interface OrderStatusUpdateForPaymentsService {
    /**
     * 같은 orderListId를 가진 주문들을 찾아 List 로 받고 stream 으로 변환 후
     * allmatch 로 order 상태가 전부 PAYMENT_COMPLETED와 같다면 true 를 반환한다.
     * 후에 해당 오더리스트는 쓸 수 없는 상태로 된다.
     **/
    void update(Long orderListId);
}
