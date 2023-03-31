package com.example.team_project.domain.domain.order.list;

import com.example.team_project.exception.InvalidAddressException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {

    default Address validateAddressId(Long addressId){
        Optional<Address> addressOptional = findById(addressId);
        if(addressOptional.isPresent()){
            return addressOptional.get();
        }
        throw new InvalidAddressException();
    }
}
