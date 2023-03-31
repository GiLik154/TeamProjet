package com.example.team_project.domain.domain.order.list.domain;

<<<<<<< HEAD
import com.example.team_project.exception.OrderListNotFoundException;
=======
import com.example.team_project.exception.OrderNotFoundException;
>>>>>>> e97cbad6628652ecf9dd4df84b65861ffe514662
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderListRepository extends JpaRepository<OrderList, Long> {

<<<<<<< HEAD
    Optional<OrderList> findByUserIdAndStatus(Long userId, boolean status);

    Optional<OrderList> findByUserIdAndId(Long userId, Long orderListId);

    Optional<OrderList> findByUserId(Long userId);
=======
    default OrderList validateOrderListId(Long orderListId){
        Optional<OrderList> orderListOptional = findById(orderListId);
        if (orderListOptional.isPresent()){
            return orderListOptional.get();
        }
        throw new OrderNotFoundException();
    }

>>>>>>> e97cbad6628652ecf9dd4df84b65861ffe514662


}
