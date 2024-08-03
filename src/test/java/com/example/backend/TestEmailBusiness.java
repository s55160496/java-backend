package com.example.backend;

import com.example.backend.businesslogic.EmailBusiness;
import com.example.backend.exception.BaseException;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TestEmailBusiness {

    @Autowired
    private EmailBusiness emailBusiness;

    @Test
    void testSendActivateEmail() throws BaseException {
        emailBusiness.sendActivateUserEmail(testSendActivateEmail.email, testSendActivateEmail.name, testSendActivateEmail.token);

    }

    interface testSendActivateEmail {
        String email = "phongsawat99@outlook.com";
        String name = "Test Alert";
        String token = "m#:dsffsdfjkojoohohsdfsdf";
    }

}
