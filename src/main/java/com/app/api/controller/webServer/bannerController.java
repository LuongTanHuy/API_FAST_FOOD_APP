package com.app.api.controller.webServer;

import com.app.api.model.bannerModel;
import com.app.api.service.FileStorageService;
import com.app.api.service._interface.bannerInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/FastFood/")
public class bannerController {
    @Autowired
    private bannerInterface bannerInterface;
    @Autowired
    private com.app.api.service._interface.accountInterface accountInterface;
    @Autowired
    private com.app.api.service.tokenService tokenService;
    @Autowired
    private FileStorageService fileStorageService;


    @GetMapping("banner")
    public String PageBanner(Model model, @CookieValue("FastFood") String token){
        int idAccount = Integer.parseInt(this.tokenService.validateTokenAndGetAccountId(token));
        model.addAttribute("account", this.accountInterface.accountProfile(idAccount,"Web"));
        model.addAttribute("title","Banner");
        model.addAttribute("listBanner", this.bannerInterface.getAll());
        model.addAttribute("file_html", "/components/bannerBody");
        model.addAttribute("component", "bannerBody");
        return "index";
    }

    @GetMapping("searchBanner")
    public String searchBanner(Model model, @CookieValue("FastFood") String token, @RequestParam("search") String search){
        int idAccount = Integer.parseInt(this.tokenService.validateTokenAndGetAccountId(token));
        model.addAttribute("account", this.accountInterface.accountProfile(idAccount,"Web"));
        model.addAttribute("title","Banner");
        model.addAttribute("listBanner", this.bannerInterface.search(search));
        model.addAttribute("file_html", "/components/bannerBody");
        model.addAttribute("component", "bannerBody");
        return "index";
    }

    @GetMapping("deleteBanner/{id}")
    public ResponseEntity<?> deleteBanner(@PathVariable("id") int id){
        this.bannerInterface.deleteBanner(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/FastFood/banner");
        return ResponseEntity.status(302).headers(headers).build();
    }

    @PostMapping("addBanner")
    public ResponseEntity<?> addBanner(@RequestParam("file") MultipartFile file,@RequestParam("text") String text){
        String fileName = this.fileStorageService.storeFile(file);
        bannerModel bannermodel = new bannerModel();
        bannermodel.setText(text);
        bannermodel.setImage(fileName);
        this.bannerInterface.addBanner(bannermodel);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/FastFood/banner");
        return ResponseEntity.status(302).headers(headers).build();
    }

    @PostMapping("updateBanner")
    public ResponseEntity<?> updateBanner(@RequestParam("file") MultipartFile file,@RequestParam("text") String text,@RequestParam("id") int id){
        bannerModel bannermodel = new bannerModel();
        bannermodel.setId(id);
        bannermodel.setText(text);
        if(!file.isEmpty()){
            String fileName = this.fileStorageService.storeFile(file);
            bannermodel.setImage(fileName);
        }
        if(this.bannerInterface.updateBanner(bannermodel)){
            HttpHeaders headers = new HttpHeaders();
            headers.add("Location", "/FastFood/banner");
            return ResponseEntity.status(302).headers(headers).build();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/FastFood/banner");
        return ResponseEntity.status(302).headers(headers).build();
    }
}
