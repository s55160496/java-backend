package com.example.backend.exception;

public class UserException extends BaseException {

    public UserException(String code) {
        super("user." + code);
    }

    public static UserException emailNull() {
        return new UserException("register.email.null");
    }

    public static UserException RequestNull() {
        return new UserException("register.requestNull.null");
    }

    public static UserException notFound() {
        return new UserException("user.not.found");
    }

    public static UserException unauthorized() {
        return new UserException("user.unauthorized");
    }

    //Create
    public static UserException createEmailNull() {
        return new UserException("create.email.null");
    }

    public static UserException createPasswordNull() {
        return new UserException("create.password.null");
    }

    public static UserException createNameNull() {
        return new UserException("create.name.null");
    }

    public static UserException createDuplicateEmail() {
        return new UserException("create.duplicate.email");
    }

    //Login

    public static UserException LoginFailEmailNotFound() {
        return new UserException("login.fail.email");
    }

    public static UserException LoginFailPasswordIncorrect() {
        return new UserException("login.fail.email.incorrect");
    }

    public static UserException LoginFailUserUnactivated() {
        return new UserException("login.fail.unactivated");
    }

    //Activate
    public static UserException activateNoToken() {
        return new UserException("activate.no.token");
    }

    public static UserException activateFail() {
        return new UserException("activate.fail");
    }

    public static UserException activateAlready() {
        return new UserException("activate.already");
    }

    public static UserException activateTokenExpire() {
        return new UserException("activate.token.expire");
    }

    //RESEND ACTIVATION EMAIL

    public static UserException resendActivateionTokenNull() {
        return new UserException("resend.activation.no.token");
    }

    public static UserException resendActivateionTokenNotFound() {
        return new UserException("resend.activation.fail");
    }

    //Update Name
    public static UserException updateNameNull() {
        return new UserException("update.name.null");
    }

}
