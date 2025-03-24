package com.app.api.controller.webServer;

import com.app.api.model.accountModel;
import com.app.api.service._interface.accountInterface;
import com.app.api.service.authenticationService;
import com.app.api.service.tokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/FastFood/")
public class loginController {
    @Autowired
    private accountInterface accountInterface;
    @Autowired
    private tokenService tokenService;
    @Autowired
    private authenticationService authenticationService;

    @GetMapping("login")
    public String PageLogin(){
        return "login";
    }

    @PostMapping("checkLogin")
    public ResponseEntity<?> checkLogin(@RequestParam("email") String email, @RequestParam("password") String password){
        accountModel _accountModel = this.authenticationService.login(email, password);
        if(_accountModel == null){
            HttpHeaders headers = new HttpHeaders();
            headers.add("Location", "/FastFood/login");
            return ResponseEntity.status(302).headers(headers).build();
        }else
        if (_accountModel.getId() != 0) {
            String token = this.tokenService.generateToken(_accountModel.getId());

            ResponseCookie cookie = ResponseCookie.from("FastFood", token)
                    .httpOnly(false)
                    .secure(false)
                    .path("/")
                    .maxAge(7 * 24 * 60 * 60)
                    .build();

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.SET_COOKIE, cookie.toString());
            if (Integer.parseInt(_accountModel.getPermission()) == 1) {
                headers.add("Location", "/FastFood/account");
            }else if(Integer.parseInt(_accountModel.getPermission()) == 0){
                headers.add("Location", "/FastFood/dashboard");
            }else{
                headers.add("Location", "/FastFood/login");
            }
            return ResponseEntity.status(302).headers(headers).build();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/FastFood/login");

        return ResponseEntity.status(302).headers(headers).build();
    }

    @GetMapping("getCookie")
    public ResponseEntity<?> checkLogin(@CookieValue("FastFood") String token) {
        return ResponseEntity.ok().body(token);
    }

    @GetMapping("logout")
    public ResponseEntity<?> logOut(){

        ResponseCookie cookie = ResponseCookie.from("FastFood", null)
                .httpOnly(false)
                .secure(false)
                .path("/")
                .maxAge(0)
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.SET_COOKIE, cookie.toString());
        headers.add("Location", "/FastFood/login");

        return ResponseEntity.status(302).headers(headers).build();
    }
}
