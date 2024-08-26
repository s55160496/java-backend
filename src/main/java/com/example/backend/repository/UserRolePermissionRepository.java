package com.example.backend.repository;

import com.example.backend.entity.UserRolePermission;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRolePermissionRepository extends CrudRepository<UserRolePermission, String> {

    Optional<UserRolePermission> findById(String id);


}
