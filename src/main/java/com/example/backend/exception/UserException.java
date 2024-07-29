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

    //Create
    public static UserException createEmailNull(){
        return new UserException("create.email.null");
    }
    public static UserException createPasswordNull(){
        return new UserException("create.password.null");
    }
    public static UserException createNameNull(){
        return new UserException("create.name.null");
    }

    public static UserException createDuplicateEmail(){
        return new UserException("create.duplicate.email");
    }

    //Login

    public static UserException LoginFailEmailNotFound(){
        return new UserException("login.fail.email");
    }

    public static UserException LoginFailPasswordIncorrect(){
        return new UserException("login.fail.email.incorrect");
    }
}
