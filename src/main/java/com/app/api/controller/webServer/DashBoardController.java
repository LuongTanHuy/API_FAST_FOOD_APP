package com.app.api.controller.webServer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller("webServerDashBoardController")
@RequestMapping("/FastFood/")
public class DashBoardController {

    @Autowired
    private com.app.api.service._interface.accountInterface accountInterface;
    @Autowired
    private com.app.api.service.tokenService tokenService;
    @Autowired
    private com.app.api.service._interface.orderItemInterface orderItemInterface;


    @GetMapping("dashboard")
    public String PageOderItemForStore(Model model, @CookieValue("FastFood") String token){
        try {
            int idAccount = Integer.parseInt(this.tokenService.validateTokenAndGetAccountId(token));
            int idStore = this.accountInterface.getAccountDetailForWeb(Integer.parseInt(this.tokenService.validateTokenAndGetAccountId(token))).getStoreModel().getId();

            model.addAttribute("account", this.accountInterface.accountProfile(idAccount, "Web"));
            model.addAttribute("title", "Thống Kê");
            model.addAttribute("quantityOrder", this.orderItemInterface.getQuantityOrder(idStore));
            model.addAttribute("priceOrder", this.orderItemInterface.getPriceOrder(idStore, 2024));
            List<Integer> years = IntStream.rangeClosed(2024, 2024 + 99).boxed().collect(Collectors.toList());
            model.addAttribute("years", years);
            model.addAttribute("file_html", "/components/dashBoardBody");
            model.addAttribute("component", "dashBoardBody");
            return "index";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("dashboard2")
    public String PageOderItemForStore2(Model model, @CookieValue("FastFood") String token, @RequestParam("year") int year){
        model.addAttribute("account", this.accountInterface.getAccountDetailForWeb(Integer.parseInt(this.tokenService.validateTokenAndGetAccountId(token))));
        model.addAttribute("title", "Thống Kê");
        model.addAttribute("quantityOrder",this.orderItemInterface.getQuantityOrder(this.accountInterface.getAccountDetailForWeb(Integer.parseInt(this.tokenService.validateTokenAndGetAccountId(token))).getStoreModel().getId()));
        model.addAttribute("priceOrder",this.orderItemInterface.getPriceOrder(this.accountInterface.getAccountDetailForWeb(Integer.parseInt(this.tokenService.validateTokenAndGetAccountId(token))).getStoreModel().getId(),year));
        List<Integer> years = IntStream.rangeClosed(2024, 2024 + 99).boxed().collect(Collectors.toList());
        model.addAttribute("years", years);
        model.addAttribute("file_html", "/components/dashBoardBody");
        model.addAttribute("component", "dashBoardBody");
        return "index";
    }
}
