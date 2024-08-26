package com.example.backend.service;

import com.example.backend.entity.Menu;
import com.example.backend.entity.UserRolePermission;
import com.example.backend.repository.UserRolePermissionRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@Log4j2
public class RoleUserPermissionService {

    private final UserRolePermissionRepository repository;

    public RoleUserPermissionService(UserRolePermissionRepository repository) {
        this.repository = repository;
    }


    public Optional<UserRolePermission> findById(String id) {
        return repository.findById(id);
    }

    public UserRolePermission Create(UserRolePermission request) throws Exception {
        if (Objects.isNull(request)) {
            throw new Exception("request.is.null");
        }
        return repository.save(request);
    }
}
