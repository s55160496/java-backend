package com.example.backend.service;

import com.example.backend.entity.User;
import com.example.backend.exception.BaseException;
import com.example.backend.exception.UserException;
import com.example.backend.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }


    public Optional<User> findByEmail(String email){
        return  repository.findByEmail(email);
    }
    
    public User update(User user){
        return repository.save(user);
    }

    public User updateName(String id , String name) throws UserException {
        Optional<User> opt = repository.findById(id);
        if(opt.isEmpty()){
           throw UserException.notFound();
        }

        User user = opt.get();
        user.setName(name);
        return repository.save(user);
    }

    public  boolean matchPassword(String rawPassword,String encodedPassword){
        return passwordEncoder.matches(rawPassword,encodedPassword);
    }


    public User create(String email,String password,String name) throws BaseException {

        //Validate
        if(Objects.isNull(email)){
            throw UserException.createEmailNull();
        }
        if(Objects.isNull(password)){
            throw UserException.createPasswordNull();
        }
        if(Objects.isNull(name)){
            throw UserException.createNameNull();
        }

        //Verify
        if(repository.existsByEmail(email)){
            throw UserException.createDuplicateEmail();
        }

        //save
        User entity = new User();
        entity.setEmail(email);
        entity.setPassword(passwordEncoder.encode(password));
        entity.setName(name);
        return repository.save(entity);
    }

    public void deleteById(String id){
        repository.deleteById(id);
    }


}
