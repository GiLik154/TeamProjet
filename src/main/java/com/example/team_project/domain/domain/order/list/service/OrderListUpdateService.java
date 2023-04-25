package com.example.team_project.domain.domain.order.list.service;

import com.example.team_project.domain.domain.address.domain.UserAddress;
import com.example.team_project.exception.OrderListNotFoundException;

public interface OrderListUpdateService {

    /**
     * userId와 orderListId를 사용해 레퍼지토리에서 찾습니다
     * 만약 해당 Id값들을 가진 orderList가 없다면 OrderListNotFoundException 발생합니다
     * 만약 orderList가 존재할시 매개변수로 받은 userAddressId로 해당 주소를 레퍼지토리에서 찾아와 업데이트 합니다
     *
     * @param userId:      사용자 고유 Id
     * @param orderListId: 주문리스트 고유 Id
     * @param userAddressId: 사용자 주소 고유 Id
     * @throws OrderListNotFoundException: 해당 주문리스트를 찾을 수 없을 떄
     **/
    void update(Long userId, Long orderListId, Long userAddressId);
    void update(Long userId, Long orderListId, Long userAddressId, Long paymentId);


}
