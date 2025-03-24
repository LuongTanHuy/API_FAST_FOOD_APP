package com.app.api.controller.api;

import com.app.api.model.accountModel;
import com.app.api.service.FileStorageService;
import com.app.api.service._interface.accountInterface;
import com.app.api.service.tokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController("apiAccountController")
@RequestMapping("/api/v1/")
public class accountController {
    private final accountInterface accountInterface;
    private final tokenService tokenService;
    private final FileStorageService fileStorageService;

    @Autowired
    public accountController(accountInterface accountInterface,
                             tokenService tokenService,
                             FileStorageService fileStorageService) {

        this.accountInterface = accountInterface;
        this.tokenService = tokenService;
        this.fileStorageService = fileStorageService;
    }

    @GetMapping("profile")
    public ResponseEntity<?> accountProfile(@RequestHeader("Authorization") String authorizationHeader){

        try {
            String token     = authorizationHeader.replace("Bearer ", "");
            int    idAccount = Integer.parseInt(this.tokenService.validateTokenAndGetAccountId(token));
            return this.accountInterface.accountProfile(idAccount,"App").getId() > 0 && this.accountInterface.accountProfile(idAccount,"App").getStatus() != 0 ?
                    ResponseEntity.status(200).body(this.accountInterface.accountProfile(idAccount,"App")):
                    ResponseEntity.status(404).body("Not found account");
        } catch (NumberFormatException e) {
            return ResponseEntity.status(404).body("Not found account - Authorization Null");
        }
    }

    @GetMapping("account/search/{information}")
    public List<accountModel> searchAccounts(@PathVariable("information") String information){
        return this.accountInterface.searchAccounts(information);
    }

    @PostMapping("update-profile")
    public ResponseEntity<?> updateProfile(
                                         @RequestHeader("Authorization") String authorizationHeader,
                                         @RequestParam("userName") String userName,
                                         @RequestParam("email") String email,
                                         @RequestParam("phone") String phone,
                                         @RequestParam("address") String address,
                                         @RequestParam("file") MultipartFile file) {

        String token  = authorizationHeader.replace("Bearer ", "");
        int idAccount = Integer.parseInt(this.tokenService.validateTokenAndGetAccountId(token));

        accountModel accountModel = new accountModel();
        accountModel.setId(idAccount);
        accountModel.setUsername(userName);
        accountModel.setEmail(email);
        accountModel.setPhone(phone);
        accountModel.setAddress(address);

        String nameImage = !file.isEmpty() ? this.fileStorageService.storeFile(file) : null;
        if (nameImage != null) { accountModel.setImage(nameImage);}

        return this.accountInterface.updateProfile(accountModel) ? ResponseEntity.status(200).body("Success") : ResponseEntity.status(500).body("Error");
    }

    @PutMapping("account/update-otp")
    public ResponseEntity<?> updateOtp(@RequestBody accountModel accountModel) {
        return this.accountInterface.updateOtp(accountModel) ? ResponseEntity.status(200).body("Success"):ResponseEntity.status(500).body("Error");
    }

    @DeleteMapping("account/delete-account/{idAccount}")
    public ResponseEntity<?> deleteAccount(@PathVariable("idAccount") int idAccount){
        return this.accountInterface.deleteAccount(idAccount) ? ResponseEntity.status(200).body("Success") : ResponseEntity.status(404).body("Not found account");
    }

    @PutMapping("account/lock-account/{idAccount}")
    public ResponseEntity<?> lockAccount(@PathVariable("idAccount") int idAccount){
        return  this.accountInterface.lockAccount(idAccount) ? ResponseEntity.status(200).body("Success") : ResponseEntity.status(404).body("Not found account");
    }

}
