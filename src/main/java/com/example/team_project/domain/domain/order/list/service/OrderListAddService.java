package com.example.team_project.domain.domain.order.list.service;

import com.example.team_project.domain.domain.order.list.domain.OrderList;
import com.example.team_project.domain.domain.payment.domain.Payment;
import com.example.team_project.exception.UserNotFoundException;

public interface OrderListAddService {

    /**
     * userId를 통해 validateUserId로 검증해 user객체를 가져옵니다
     * 해당 userId를 가진 user가 있을경우 가져온 user객체와 매개변수로 받는 userAddress와 paymentMethod를 가지고 orderLsit를 생성합니다
     * 생성후 레퍼지토리에 저장후 생성한 orderList를 리턴해줍니다
     * 만약 해당 userId를 가진 user가 없을경우 UserNotFoundException을 발생시킵니다
     *
     * @param userId:사용자     고유 Id
     * @param userAddressId: 사용자 주소 고유 Id
     * @param paymentId:     결제방법 고유 Id
     * @return userId를 통해 가져온 user객체와 매개변수로 받은 userAddress와 paymentMethod를 가진 orderList
     * @throws UserNotFoundException:유저를 찾을 수 없을 때
     **/
    OrderList add(Long userId, Long userAddressId, Long paymentId);

}
