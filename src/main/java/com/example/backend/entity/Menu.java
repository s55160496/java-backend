package com.example.backend.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = false)
@Data
@Entity(name = "m_menu")
public class Menu extends BaseEntity implements Serializable {

    @Column(length = 36, nullable = false)
    private String submenuId;

    @Column(length = 36, nullable = false)
    private String order;

    @Column(nullable = false, unique = true, length = 120)
    private String name_en;

    @Column(nullable = false, unique = true, length = 120)
    private String name_th;

    @Column(nullable = true, length = 60)
    private String icon;

    @Column(nullable = true, length = 60)
    private String path;

    private boolean isActive;

    private boolean isBack;
    

}
