//package com.example.team_project.domain.domain.user.service.purchase;
//
//import com.example.team_project.domain.domain.address.domain.UserAddress;
//import com.example.team_project.domain.domain.order.item.domain.Order;
//import com.example.team_project.domain.domain.product.product.domain.Product;
//import com.example.team_project.domain.domain.user.domain.User;
//import com.example.team_project.domain.domain.user.domain.UserRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//import java.util.Optional;
//
//@Service
//@Transactional
//@RequiredArgsConstructor
//public class UserPurchaseServiceImpl implements UserPurchaseService{
//
//    private final UserRepository userRepository;
//
//    @Override
//    public void purchaseItem(Long userId, Product product){
//        Optional<User> optionalUser = userRepository.findById(userId);
//        if (optionalUser.isPresent()) {
//            User user = optionalUser.get();
//            user.purchaseProduct(product);
//            userRepository.save(user);
//        } else {
//            throw new RuntimeException("User not found");
//        }
//    }
//
//
//    @Override
//    public void cancelPurchaseItem(Long userId, Product product){
//        Optional<User> optionalUser = userRepository.findById(userId);
//        if (optionalUser.isPresent()) {
//            User user = optionalUser.get();
//            user.cancelProduct(product);
//            userRepository.save(user);
//        } else {
//            throw new RuntimeException("User not found");
//        }
//    }
//
//    @Override
//    public List<UserAddress> getAddressList(Long userId){
//        Optional<User> optionalUser = userRepository.findById(userId);
//        if (optionalUser.isPresent()) {
//            User user = optionalUser.get();
//            return user.getAddressList();
//        } else {
//            throw new RuntimeException("User not found");
//        }
//    }
//
//    @Override
//    public List<Order> getPurchaseItemList(Long userId){
//        Optional<User> optionalUser = userRepository.findById(userId);
//        if (optionalUser.isPresent()) {
//            User user = optionalUser.get();
//            return user.getPurchaseItemList();
//        } else {
//            throw new RuntimeException("User not found");
//        }
//    }
//}
