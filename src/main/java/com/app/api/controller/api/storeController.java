package com.app.api.controller.api;

import com.app.api.model.storeModel;
import com.app.api.service.FileStorageService;
import com.app.api.service._interface.accountInterface;
import com.app.api.service._interface.storeInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/")
public class storeController {

    @Autowired
    private com.app.api.service.tokenService tokenService;
    @Autowired
    private FileStorageService fileStorageService;
    @Autowired
    private storeInterface storeInterface;
    @Autowired
    private accountInterface accountInterface;


    @PostMapping("add-store")
    public ResponseEntity<?> addStore(@RequestHeader("Authorization") String authorizationHeader,
                                        @RequestParam("name") String name,
                                        @RequestParam("address") String address,
                                        @RequestParam("email") String email,
                                        @RequestParam("phone") String phone,
                                        @RequestParam("file") MultipartFile file) {
        String token = authorizationHeader.replace("Bearer ", "");
       int idAccount = Integer.parseInt(this.tokenService.validateTokenAndGetAccountId(token));

        storeModel newStoreModel = new storeModel();
        newStoreModel.setName(name);
        newStoreModel.setAddress(address);
        newStoreModel.setEmail(email);
        newStoreModel.setPhone(phone);
        newStoreModel.setImage(this.fileStorageService.storeFile(file));
        newStoreModel.setStatus(0);

        int idStore = this.storeInterface.addStore(newStoreModel);

        return this.accountInterface.updateIdStore(idStore,idAccount) ?
                ResponseEntity.status(200).body("ok") :
                ResponseEntity.status(500).body("Error");
    }

}
