package com.example.backend.service;

import com.example.backend.entity.Menu;
import com.example.backend.entity.UserRole;
import com.example.backend.repository.UserRoleRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@Log4j2
public class RoleUserService {

    private final UserRoleRepository repository;

    public RoleUserService(UserRoleRepository repository) {
        this.repository = repository;
    }


    public Optional<UserRole> findById(String id) {
        return repository.findById(id);
    }

    public UserRole Create(UserRole request) throws Exception {
        if (Objects.isNull(request)) {
            throw new Exception("request.is.null");
        }
        return repository.save(request);
    }
}
