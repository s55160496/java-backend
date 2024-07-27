package com.example.backend.businesslogic;

import com.example.backend.exception.BaseException;
import com.example.backend.exception.UserException;
import com.example.backend.model.MRegisterRequest;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class TestBusiness {

    public String register (MRegisterRequest request) throws BaseException {
    if(request == null){
        throw UserException.RequestNull();
}

    if(Objects.isNull(request.getEmail())){
        throw  UserException.emailNull();
    }

        return  "";
    }

}
