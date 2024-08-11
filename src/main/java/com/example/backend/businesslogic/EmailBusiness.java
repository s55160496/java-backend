package com.example.backend.businesslogic;

import com.example.backend.exception.BaseException;
import com.example.backend.exception.EmailException;
import com.example.common.EmailRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;

@Service
@Log4j2
public class EmailBusiness {

    private final KafkaTemplate<String,EmailRequest> Kafkatemplate;

    public EmailBusiness(KafkaTemplate<String, EmailRequest> kafkatemplate) {
        Kafkatemplate = kafkatemplate;
    }

    public void sendActivateUserEmail(String email, String name, String token) throws BaseException {

        String html;
        try {
            html = ReadEmailTemplate("email-activate-user.html");

        } catch (IOException e) {
            throw EmailException.templateNotFound();
        }

        log.info("Token = " + token);

        String finallink = "http//localhost:4200/activate/"+ token;
        html  = html.replace("${P_NAME}",name);
        html  = html.replace("${P_LINK}",finallink);


        EmailRequest request = new EmailRequest();
        request.setTo(email);
        request.setSubject("Please Activate your account");
        request.setContent(html);


        CompletableFuture<SendResult<String, EmailRequest>> future = Kafkatemplate.send("activation-email", request).toCompletableFuture();
        future
                .exceptionally(throwable -> {
                    log.error("Kafka send fail");
                    log.error(throwable);

                    return null;
                })
                .thenAccept(result -> {
                    log.info("Kafka send success");
                    log.info(result);
                });

      //  emailService.Send(email, subject, html);

    }

    private String ReadEmailTemplate(String filename) throws IOException {
        File file = ResourceUtils.getFile("classpath:email/" + filename);
        return FileCopyUtils.copyToString(new FileReader(file));
    }
}
