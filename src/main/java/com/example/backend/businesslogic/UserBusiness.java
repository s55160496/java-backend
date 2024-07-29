package com.example.backend.businesslogic;

import com.example.backend.entity.User;
import com.example.backend.exception.BaseException;
import com.example.backend.exception.FileException;
import com.example.backend.exception.UserException;
import com.example.backend.mapper.UserMapper;
import com.example.backend.model.MLoginRequest;
import com.example.backend.model.MRegisterRequest;
import com.example.backend.model.MRegisterResponse;
import com.example.backend.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UserBusiness {

    private final UserService userService;
    private final UserMapper userMapper;


    public UserBusiness(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    public String login(MLoginRequest request) throws BaseException {

        // validate Request


        //Verify database
        Optional<User> opt = userService.findByEmail(request.getEmail());
        if (opt.isEmpty()) {
            //throw login fail
            throw UserException.LoginFailEmailNotFound();
        }

        User user = opt.get();
        if (!userService.matchPassword(request.getPassword(), user.getPassword())) {
            //throw password incorrect
            throw UserException.LoginFailPasswordIncorrect();
        }

        //TODO : generate JWT
        String token = "TODO : generate JWT";

        return token;
    }


    public MRegisterResponse register(MRegisterRequest request) throws BaseException {
        User user = userService.create(request.getEmail(), request.getPassword(), request.getName());

        //TO DO Mapper

        return userMapper.toRegisterResponse(user);
    }

    public String UploadProfilePicture(MultipartFile file) throws BaseException, IOException {
        //validate file
        if (file == null) {

            throw FileException.fileNull();
        }

        //validate size
        if (file.getSize() > 1048576 * 2) {
            //throw error
            throw FileException.fileMaxSize();
        }

        String ContentType = file.getContentType();
        if (ContentType == null) {
            throw FileException.unSupported();
        }

        List<String> SupportedcontentTypes = Arrays.asList("image/jpeg", "image/png");
        if (!SupportedcontentTypes.contains(ContentType)) {
            //throw error
            throw FileException.unSupported();
        }

        //TODO : upload file File Storage (AWS S3,etc ..)
        try {
            byte[] bytes = file.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }


}
