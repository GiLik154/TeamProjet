package com.example.team_project.domain.domain.order.list.service;

public interface OrderListCancelService {


    /**
     * findCancelableOrders로 취소 가능한 주문들을 리스트로 받아온 뒤
     * validateCancelableOrders 통해 취소 가능한 주문이 없다면 true, 있다면 false를 반환
     * true의 경우 취소 할 수 있는 주문이 없다고 익셉션이 발생한다
     * false의 경우 forEach를 통해 취소가능 한 order의 List를 순회하며 상태를 CANCELED로 바꿔준다
     *
     * @param orderListId:주문리스트 고유 Id
     **/
    void cancel(Long orderListId);
}
