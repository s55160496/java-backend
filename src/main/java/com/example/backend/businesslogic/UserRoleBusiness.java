package com.example.backend.businesslogic;

import com.example.backend.entity.Menu;
import com.example.backend.entity.UserRole;
import com.example.backend.service.MenuService;
import com.example.backend.service.RoleUserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class UserRoleBusiness {
    private final RoleUserService roleUserService;

    public UserRoleBusiness(RoleUserService roleUserService) {
        this.roleUserService = roleUserService;
    }

    public void insert(UserRole request) throws Exception {
        roleUserService.Create(request);
    }
}
