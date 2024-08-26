package com.example.backend.businesslogic;

import com.example.backend.entity.Menu;
import com.example.backend.entity.User;
import com.example.backend.exception.BaseException;
import com.example.backend.exception.FileException;
import com.example.backend.exception.UserException;
import com.example.backend.mapper.UserMapper;
import com.example.backend.model.*;
import com.example.backend.service.MenuService;
import com.example.backend.service.TokenService;
import com.example.backend.service.UserService;
import com.example.backend.util.SecurityUtil;
import io.netty.util.internal.StringUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Log4j2
@Service
public class MenuBusiness {
    private final MenuService menuService;

    public MenuBusiness(MenuService menuService) {
        this.menuService = menuService;
    }

    public void insert(Menu request) throws Exception {
        menuService.Create(request);
        //TO DO Mapper
        //sendEmail(user);
    }
}
