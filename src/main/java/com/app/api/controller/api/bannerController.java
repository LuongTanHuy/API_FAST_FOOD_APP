package com.app.api.controller.api;

import com.app.api.model.bannerModel;
import com.app.api.service._interface.bannerInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("apiBannerController")
@RequestMapping("/api/v1/")
public class bannerController {
    @Autowired
    private bannerInterface bannerInterface;

    @GetMapping("banner")
    public List<bannerModel> listBanner(){
        return this.bannerInterface.getAll();
    }
}
