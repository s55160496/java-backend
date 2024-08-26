package com.example.backend.service;

import com.example.backend.entity.Menu;
import com.example.backend.entity.User;
import com.example.backend.exception.BaseException;
import com.example.backend.exception.UserException;
import com.example.backend.repository.MenuRepository;
import com.example.backend.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.apache.tomcat.util.modeler.Util;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.swing.text.Utilities;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

@Service
@Log4j2
public class MenuService {

    private final MenuRepository repository;

    public MenuService(MenuRepository repository) {
        this.repository = repository;
    }


    public Optional<Menu> findById(String id) {
        return repository.findById(id);
    }

    public Menu Create(Menu request) throws Exception {
        if (Objects.isNull(request)) {
            throw new Exception("request.is.null");
        }
        return repository.save(request);
    }
    
}