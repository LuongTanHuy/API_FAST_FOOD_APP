package com.app.api.controller.webServer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller("webServerOrderItemController")
@RequestMapping("/FastFood/")
public class orderItemController {
    @Autowired
    private com.app.api.service._interface.accountInterface accountInterface;
    @Autowired
    private com.app.api.service.tokenService tokenService;
    @Autowired
    private com.app.api.service._interface.orderItemInterface orderItemInterface;
    @Autowired
    private com.app.api.service._interface.orderInterface orderInterface;

    @GetMapping("orderItem")
    public String PageOderItemForStore(Model model, @CookieValue("FastFood") String token){
        model.addAttribute("account", this.accountInterface.getAccountDetailForWeb(Integer.parseInt(this.tokenService.validateTokenAndGetAccountId(token))));
        model.addAttribute("title", "Đơn Mới");
        model.addAttribute("listOderItem", this.orderItemInterface.listOderItemOfStore(this.accountInterface.getAccountDetailForWeb(Integer.parseInt(this.tokenService.validateTokenAndGetAccountId(token))).getStoreModel().getId(),1));
        model.addAttribute("file_html", "/components/orderItemBody");
        model.addAttribute("component", "orderItemBody");
        return "index";
    }

    @GetMapping("shippingOrderItem")
    public String ShippingOrderItem(Model model, @CookieValue("FastFood") String token){
        model.addAttribute("account", this.accountInterface.getAccountDetailForWeb(Integer.parseInt(this.tokenService.validateTokenAndGetAccountId(token))));
        model.addAttribute("title", " Đang Giao");
        model.addAttribute("listOderItem", this.orderItemInterface.listOderItemOfStore(this.accountInterface.getAccountDetailForWeb(Integer.parseInt(this.tokenService.validateTokenAndGetAccountId(token))).getStoreModel().getId(),2));
        model.addAttribute("file_html", "/components/orderItemBody");
        model.addAttribute("component", "orderItemBody");
        return "index";
    }

    @GetMapping("finishOrderItem")
    public String finishOrderItem(Model model, @CookieValue("FastFood") String token){
        model.addAttribute("account", this.accountInterface.getAccountDetailForWeb(Integer.parseInt(this.tokenService.validateTokenAndGetAccountId(token))));
        model.addAttribute("title", "Đã Giao");
        model.addAttribute("listOderItem", this.orderItemInterface.listOderItemOfStore(this.accountInterface.getAccountDetailForWeb(Integer.parseInt(this.tokenService.validateTokenAndGetAccountId(token))).getStoreModel().getId(),3));
        model.addAttribute("file_html", "/components/orderItemBody");
        model.addAttribute("component", "orderItemBody");
        return "index";
    }

    @GetMapping("cancelOrderItem")
    public String cancelOrderItem(Model model, @CookieValue("FastFood") String token){
        model.addAttribute("account", this.accountInterface.getAccountDetailForWeb(Integer.parseInt(this.tokenService.validateTokenAndGetAccountId(token))));
        model.addAttribute("title", "Hủy Đơn");
        model.addAttribute("listOderItem", this.orderItemInterface.listOderItemOfStore(this.accountInterface.getAccountDetailForWeb(Integer.parseInt(this.tokenService.validateTokenAndGetAccountId(token))).getStoreModel().getId(),4));
        model.addAttribute("file_html", "/components/orderItemBody");
        model.addAttribute("component", "orderItemBody");
        return "index";
    }

    @PostMapping("changeStatus")
    public ResponseEntity<?> changeStatusOnOrderItem(@RequestParam("status") int status,@RequestParam("id_order") int id_order){
        this.orderInterface.updateStatus(id_order,status);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/FastFood/orderItem");
        return ResponseEntity.status(302).headers(headers).build();
    }

}
