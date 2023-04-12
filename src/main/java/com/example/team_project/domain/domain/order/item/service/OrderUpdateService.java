package com.example.team_project.domain.domain.order.item.service;


public interface OrderUpdateService {

    /**
     * productId와 orderId로 validate로 검증하여 각 객체를 얻어온 뒤 OrderToProduct의 update메소드를 통해 수정해준다
     * 이 때 상품의 재고량이 떨어졌거나 부족할시 OutOfStockException 발샐
     * 충분 할 시 매개변수로 받은 product와 quantity로 주문정보가 수정됩니다
     *
     * @param productId: 상품 고유 ID
     * @param orderId:   주문 고유 ID
     * @param quantity:  주문 개수
     * @throws @ProductNotFoundException:해당 상품을 찾을 수 없을 때
     * @throws @OrderNotFoundException:     해당 주문을 찾을 수 없을 때
     * @throws @OutOfStockException:        주문하려는 상품의 재고가 떨어졌거나 부족할 때
     **/
    void update(Long productId, Long orderId, int quantity);

}
