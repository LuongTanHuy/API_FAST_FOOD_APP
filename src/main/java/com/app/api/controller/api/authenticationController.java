package com.app.api.controller.api;

import com.app.api.model.accountModel;
import com.app.api.service.accountService;
import com.app.api.service.authenticationService;
import com.app.api.service.emailService;
import com.app.api.service.tokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/api/v1/auth/")

public class authenticationController {
    private final authenticationService authenticationService;
    private final emailService emailService;
    private final tokenService tokenService;
    private final accountService accountService;

    @Autowired
    public authenticationController(authenticationService authenticationService,
                                    emailService emailService, tokenService tokenService,
                                    accountService accountService) {
        this.authenticationService = authenticationService;
        this.emailService = emailService;
        this.tokenService = tokenService;
        this.accountService = accountService;
    }

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody accountModel accountModel) {
        accountModel _accountModel = this.authenticationService.login(accountModel.getEmail(), accountModel.getPassword());

        if (_accountModel != null) {
            if (_accountModel.getStatus() == 1) {
                String token = this.tokenService.generateToken(_accountModel.getId());
                return ResponseEntity.status(200).body(token);
            } else {
                return ResponseEntity.status(403).body("Account is inactive or locked");
            }
        } else {
            return ResponseEntity.status(404).body("Invalid email or password");
        }
    }


    @PostMapping("signUp")
    public ResponseEntity<String> signUp(@RequestBody accountModel accountModel) {
        accountModel _accountModel = this.authenticationService.signUp(accountModel.getEmail(), accountModel.getPassword());
        return  (_accountModel.getId() > 0) ?
                ResponseEntity.status(200).body(this.tokenService.generateToken(_accountModel.getId())) :
                (ResponseEntity<String>) ResponseEntity.status(500);
    }

    @PostMapping("sendOtp")
    public ResponseEntity<String> sendOtp(@RequestBody accountModel accountModel,
                                          @RequestHeader("Authorization") String authorizationHeader) {
        String idAccount = this.tokenService.validateTokenAndGetAccountId(authorizationHeader.replace("Bearer ", ""));
        emailService.sendOtpEmail(accountModel.getEmail(), idAccount);
        return ResponseEntity.status(200).body("OTP sent successfully");
    }

    @PostMapping("checkOtp")
    public ResponseEntity<String> checkOtp(@RequestBody accountModel accountModel,
                                           @RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.replace("Bearer ", "");
        int idAccount = Integer.parseInt(this.tokenService.validateTokenAndGetAccountId(token));
        if (token == null) {
            return  String.valueOf(idAccount) == null ?
                    ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token") :
                    ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token not provided");
        }

        return  this.accountService.checkOtp(idAccount, accountModel.getOtp()) ?
                ResponseEntity.status(200).body("OTP Correct") :
                ResponseEntity.status(500).body("OTP Incorrect");
    }

    @PostMapping("checkEmail")
    public ResponseEntity<String> checkEmail(@RequestBody accountModel accountModel) {
        return this.accountService.checkEmail(accountModel.getEmail()) ?
                ResponseEntity.status(200).body("Success") :
                ResponseEntity.status(404).body("Not Found");
    }

    @PostMapping("idAccount")
    public ResponseEntity<?> idAccount(@RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.replace("Bearer ", "");
        int idAccount = Integer.parseInt(this.tokenService.validateTokenAndGetAccountId(token));
        if (token == null) {
            return ResponseEntity.status(200).body(0);
        }

        return ResponseEntity.status(200).body(idAccount);
    }
}
