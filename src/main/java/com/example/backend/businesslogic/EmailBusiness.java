package com.example.backend.businesslogic;

import com.example.backend.exception.BaseException;
import com.example.backend.exception.EmailException;
import com.example.backend.service.EmailService;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

@Service
public class EmailBusiness {
    private final EmailService emailService;

    public EmailBusiness(EmailService emailService) {
        this.emailService = emailService;
    }

    public void sendActivateUserEmail(String email, String name, String token) throws BaseException {

        String html;
        try {
            html = ReadEmailTemplate("email-activate-user.html");

        } catch (IOException e) {
            throw EmailException.templateNotFound();
        }
        String finallink = "http//localhost:4200/activate/"+ token;
        html  = html.replace("${P_NAME}",name);
        html  = html.replace("${LINK}",finallink);

        String subject = name;

        emailService.Send(email, subject, html);

    }

    private String ReadEmailTemplate(String filename) throws IOException {
        File file = ResourceUtils.getFile("classpath:email/" + filename);
        return FileCopyUtils.copyToString(new FileReader(file));
    }
}
