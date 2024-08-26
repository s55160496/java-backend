package com.example.backend.businesslogic;

import com.example.backend.entity.User;
import com.example.backend.exception.BaseException;
import com.example.backend.exception.FileException;
import com.example.backend.exception.UserException;
import com.example.backend.mapper.UserMapper;
import com.example.backend.model.*;
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
public class UserBusiness {

    private final EmailBusiness emailBusiness;
    private final UserService userService;
    private final UserMapper userMapper;
    private final TokenService tokenService;

    public UserBusiness(EmailBusiness emailBusiness, UserService userService, UserMapper userMapper, TokenService tokenService) {
        this.emailBusiness = emailBusiness;
        this.userService = userService;
        this.userMapper = userMapper;
        this.tokenService = tokenService;
    }

    public MLoginResponse login(MLoginRequest request) throws BaseException {

        // validate Request


        //Verify database
        Optional<User> opt = userService.findByEmail(request.getEmail());
        if (opt.isEmpty()) {
            //throw login fail
            throw UserException.LoginFailEmailNotFound();
        }

        User user = opt.get();

        //verify password
        if (!userService.matchPassword(request.getPassword(), user.getPassword())) {
            //throw password incorrect
            throw UserException.LoginFailPasswordIncorrect();
        }

        //validate activate status
        if (!user.isActivated()) {
            throw UserException.LoginFailUserUnactivated();
        }

        MLoginResponse response = new MLoginResponse();
        response.setToken(tokenService.tokenize(user));
        return response;
    }

    public String refreshToken() throws BaseException {
        Optional<String> opt = SecurityUtil.getCurrentUserId();
        if (opt.isEmpty()) {
            throw UserException.unauthorized();
        }
        String userId = opt.get();


        Optional<User> optuser = userService.findById(userId);
        if (optuser.isEmpty()) {
            throw UserException.notFound();
        }

        User user = optuser.get();
        return tokenService.tokenize(user);
    }


    public MRegisterResponse register(MRegisterRequest request) throws BaseException {
        String token = SecurityUtil.generateToken();
        User user = userService.create(request.getEmail(), request.getPassword(), request.getName(), token, nextMinute(30));

        //TO DO Mapper
        //sendEmail(user);

        return userMapper.toRegisterResponse(user);
    }


    public MActivateResponse activate(MActivateRequest request) throws BaseException {
        String token = request.getToken();

        if (StringUtil.isNullOrEmpty(token)) {
            throw UserException.activateNoToken();
        }

        Optional<User> opt = userService.findByToken(token);

        if (opt.isEmpty()) {
            throw UserException.activateFail();
        }

        User user = opt.get();

        if (user.isActivated()) {
            throw UserException.activateAlready();
        }

        Date now = new Date();
        Date expireDate = user.getTokenExpire();
        if (now.after(expireDate)) {

            //TODO re-email

            throw UserException.activateTokenExpire();
        }
        user.setActivated(true);
        userService.update(user);

        MActivateResponse mActivateResponse = new MActivateResponse();
        mActivateResponse.setSuccess(true);
        return mActivateResponse;
    }

    public void resendActivationEmail(MResendActivationEmailRequest request) throws BaseException {
        String token = request.getToken();
        if (StringUtil.isNullOrEmpty(token)) {
            throw UserException.resendActivateionTokenNull();
        }

        Optional<User> opt = userService.findByToken(token);

        if (opt.isEmpty()) {
            throw UserException.resendActivateionTokenNotFound();
        }

        User user = opt.get();

        if (user.isActivated()) {
            throw UserException.activateAlready();
        }

        user.setToken(SecurityUtil.generateToken());
        user.setTokenExpire(nextMinute(30));

        user = userService.update(user);

        //sendEmail(user);

    }

    private Date nextMinute(int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, minute);
        return calendar.getTime();
    }

//    private void sendEmail(User user) {
//
//        // TODO Generate Token
//        String token = user.getToken();
//
//        try {
//            emailBusiness.sendActivateUserEmail(user.getEmail(), user.getName(), token);
//        } catch (BaseException e) {
//            throw new RuntimeException(e);
//        }
//
//    }

    public MUserProfile getUserProfile() throws BaseException {
        Optional<String> opt = SecurityUtil.getCurrentUserId();
        if (opt.isEmpty()) {
            throw UserException.unauthorized();
        }

        String UserId = opt.get();
        Optional<User> optUser = userService.findById(UserId);
        if (optUser.isEmpty()) {
            throw UserException.notFound();
        }

        return userMapper.toUserProfile(optUser.get());
    }

    public MUserProfile updateUserProfile(MUpdateProfileRequest request) throws BaseException {
        Optional<String> opt = SecurityUtil.getCurrentUserId();
        if (opt.isEmpty()) {
            throw UserException.unauthorized();
        }
        String UserId = opt.get();

        //validate
        if (ObjectUtils.isEmpty(request.getName())) {
            throw UserException.updateNameNull();
        }

        User user = userService.updateName(UserId, request.getName());
        return userMapper.toUserProfile(user);
    }

    public void deleteAccountById() throws BaseException {
        Optional<String> opt = SecurityUtil.getCurrentUserId();
        if (opt.isEmpty()) {
            throw UserException.unauthorized();
        }
        String userId = opt.get();

        if (ObjectUtils.isEmpty(userId)) {
            throw UserException.updateNameNull();
        }
        userService.deleteById(userId);
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
