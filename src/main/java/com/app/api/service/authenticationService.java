package com.app.api.service;

import com.app.api.model.accountModel;
import com.app.api.repository._interface.accountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Configuration
public class authenticationService {

    @Autowired
    private accountRepository accountRepository;
    private BCryptPasswordEncoder passwordEncoder;

    public authenticationService() {
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public accountModel login(String email,String password) {
        List<accountModel> accountModels = this.accountRepository.findByEmail(email);
        if (!accountModels.isEmpty()) {
            for (accountModel accountModel : accountModels) {
                if (this.passwordEncoder.matches(password, accountModel.getPassword())) {
                    return accountModel;
                }
            }
        }
        return null;
    }

    public accountModel signUp(String Email, String Password) {
        accountModel accountModel = new accountModel();
        accountModel.setUsername("user");
        accountModel.setStatus(0);
        accountModel.setEmail(Email);
        accountModel.setImage("imagedefault.jpg");
        accountModel.setPassword(this.passwordEncoder.encode(Password));
        accountModel.setPermission("2");
        return this.accountRepository.save(accountModel);
    }

}
