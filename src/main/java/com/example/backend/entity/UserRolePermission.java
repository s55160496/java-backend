package com.example.backend.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity(name = "m_user_role_permission")
public class UserRolePermission extends BaseEntity implements Serializable {

    @Column(length = 36, nullable = false)
    private String roleId;

    @Column(length = 36, nullable = false)
    private String menuId;

//    @ManyToOne
//    @JoinColumn(name = "m_menu", nullable = false)
//    private Menu menu;
//
//    @ManyToOne
//    @JoinColumn(name = "m_roleUser", nullable = false)
//    private RoleUser roleUser;
}
