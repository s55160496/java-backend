package com.example.backend.repository;

import com.example.backend.entity.UserRole;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRoleRepository extends CrudRepository<UserRole, String> {

    Optional<UserRole> findById(String name);


}
