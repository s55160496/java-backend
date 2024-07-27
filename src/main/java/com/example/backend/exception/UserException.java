package com.example.backend.exception;

public class UserException extends BaseException{

    public UserException(String code) {
        super("user." + code);
    }

    public static UserException emailNull(){
        return new UserException("register.email.null");
    }
    public static UserException RequestNull(){
        return new UserException("register.requestNull.null");
    }
}
