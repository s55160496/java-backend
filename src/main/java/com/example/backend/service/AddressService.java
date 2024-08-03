package com.example.backend.service;

import com.example.backend.entity.Address;
import com.example.backend.entity.User;
import com.example.backend.repository.AddressRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class AddressService {

    private final AddressRepository repository;

    public AddressService(AddressRepository repository) {
        this.repository = repository;
    }


    public List<Address> findByUser(User user) {
        return repository.findByUser(user);
    }

    public Address create(User user, String line1, String line2, String zipcode) {
        Address entity = new Address();

        entity.setUser(user);
        entity.setLine1(line1);
        entity.setLine2(line2);
        entity.setZipcode(zipcode);

        return repository.save(entity);
    }
//
//    public List<Address> createArr(User user, List<Address> addressList) {
//
//        List<Address> enList= new ArrayList<>();
//        addressList.forEach(address -> {
//            Address entity = new Address();
//
//            entity.setUser(user);
//            entity.setLine1(address.getLine1());
//            entity.setLine2(address.getLine2());
//            entity.setZipcode(address.getZipcode());
//            enList.add(entity);
//            //repository.save(entity);
//        });
//
//        return repository.saveAll(enList);
//    }

}
