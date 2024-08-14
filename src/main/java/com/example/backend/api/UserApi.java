package com.example.backend.api;

import com.example.backend.businesslogic.UserBusiness;
import com.example.backend.exception.BaseException;
import com.example.backend.model.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/user")
public class UserApi {

    private final UserBusiness business;

    public UserApi(UserBusiness business) {
        this.business = business;
    }

    @PostMapping("/test-request")
    public ResponseEntity<String> testPostRequest() {
        return ResponseEntity.ok("POST request successful");
    }

    @PostMapping("/login")
    public ResponseEntity<MLoginResponse> login(@RequestBody MLoginRequest request) throws BaseException {
        MLoginResponse response = business.login(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<MRegisterResponse> register(@RequestBody MRegisterRequest request) throws BaseException {

        MRegisterResponse response = business.register(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/activate")
    public ResponseEntity<MActivateResponse> activate(@RequestBody MActivateRequest request) throws BaseException {
        MActivateResponse response = business.activate(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/resend-activation-email")
    public ResponseEntity<Void> activate(@RequestBody MResendActivationEmailRequest request) throws BaseException {
        business.resendActivationEmail(request);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("refresh-token")
    public ResponseEntity<String> refreshToken() throws BaseException {
        String token = business.refreshToken();
        return ResponseEntity.ok(token);
    }

    @PostMapping
    public ResponseEntity<String> uploadProfilePicture(@RequestPart MultipartFile file) throws BaseException, IOException {
        String response = business.UploadProfilePicture(file);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/profile")
    public ResponseEntity<MUserProfile> getUserProfile() throws BaseException {
        MUserProfile userProfile = business.getUserProfile();
        return ResponseEntity.ok(userProfile);
    }

    @PutMapping("/update-profile")
    public ResponseEntity<MUserProfile> updateUserProfile(@RequestBody MUpdateProfileRequest request) throws BaseException {
        MUserProfile userProfile = business.updateUserProfile(request);
        return ResponseEntity.ok(userProfile);
    }

    @DeleteMapping("/test-delete")
    public ResponseEntity<Void> deleteAccount() throws BaseException {
        business.deleteAccountById();
        return ResponseEntity.ok().build();
    }

}
