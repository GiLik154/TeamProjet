package com.example.team_project.domain.domain.order.list.service;

import com.example.team_project.domain.domain.address.domain.UserAddress;
import com.example.team_project.exception.NotFoundAddressException;
import com.example.team_project.exception.OrderListNotFoundException;
import com.example.team_project.exception.UserNotFoundException;

public interface OrderListUpdateService {

    /**
     * 유저 고유Id와 주문리스트 고유Id를 각각 검사후 존재할시 매개변수로 받은 유저주소값으로 주문리스트의 주소지를 업데이트함
     *
     * @param userId:        사용자 고유 Id
     * @param orderListId:   주문리스트 고유 Id
     * @param userAddressId: 사용자 주소 고유 Id
     * @throws UserNotFoundException:      해당 유저를 찾을 수 없을떄
     * @throws OrderListNotFoundException: 해당 주문리스트를 찾을 수 없을떄
     * @throws NotFoundAddressException:   해당 주소를 찾을 수 없을때
     **/
    void update(Long userId, Long orderListId, Long userAddressId);

    /**
     * 유저 고유Id와 주문리스트 고유Id를 각각 검사후 존재할시 매개변수로 받은 유저주소값으로 주문리스트의 주소지를 업데이트함
     *
     * @param userId:        사용자 고유 Id
     * @param orderListId:   주문리스트 고유 Id
     * @param userAddressId: 사용자 주소 고유 Id
     * @throws UserNotFoundException:      해당 유저를 찾을 수 없을떄
     * @throws OrderListNotFoundException: 해당 주문리스트를 찾을 수 없을떄
     * @throws NotFoundAddressException:   해당 주소를 찾을 수 없을때
     **/
    void update(Long userId, Long orderListId, Long userAddressId, Long paymentId);


}
