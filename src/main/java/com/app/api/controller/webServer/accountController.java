package com.app.api.controller.webServer;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller("webServerAccountController")
@RequestMapping("/FastFood/")
public class accountController {

    @Autowired
    private com.app.api.service._interface.accountInterface accountInterface;
    @Autowired
    private com.app.api.service.tokenService tokenService;


    @GetMapping("account")
    public String PageAccount(Model model,
                              @CookieValue(value = "FastFood", required = false) String token,
                              @RequestParam(defaultValue = "0") int page) {
        // Kiểm tra token có hợp lệ không
        if (token == null || token.isEmpty()) {
            return "redirect:/FastFood/login";
        }

        int idAccount;
        try {
            idAccount = Integer.parseInt(this.tokenService.validateTokenAndGetAccountId(token));
        } catch (Exception e) {
            return "redirect:/login"; // Chuyển hướng nếu token không hợp lệ
        }

        int size = 2;
        int totalAccounts = this.accountInterface.totalAccount();
        int totalPages = (int) Math.ceil((double) totalAccounts / size); // Tính số trang chính xác

        // Kiểm tra page có hợp lệ không
        if (page < 0 || page >= totalPages) {
            page = 0; // Đặt về trang đầu nếu page không hợp lệ
        }

        Pageable pageable = PageRequest.of(page, size);

        // Thêm các thuộc tính vào model
        model.addAttribute("account", this.accountInterface.accountProfile(idAccount, "Web"));
        model.addAttribute("title", "Tài Khoản");
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        model.addAttribute("listAccount", this.accountInterface.pageAccounts(pageable));
        model.addAttribute("file_html", "/components/accountBody");
        model.addAttribute("component", "accountBody");

        return "index";
    }

    @GetMapping("lockOrUnlockAccount/{id}")
    public ResponseEntity<?> lockOrUnlockAccount(@PathVariable("id") int id){
        if(this.accountInterface.lockAccount(id)){
            HttpHeaders headers = new HttpHeaders();
            headers.add("Location", "/FastFood/account");
            return ResponseEntity.status(302).headers(headers).build();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/FastFood/account");
        return ResponseEntity.status(302).headers(headers).build();
    }

    @GetMapping("searchAccount")
    public String searchAccount(Model model, @CookieValue("FastFood") String token,@RequestParam("search") String search){
        int idAccount = Integer.parseInt(this.tokenService.validateTokenAndGetAccountId(token));


        model.addAttribute("account", this.accountInterface.accountProfile(idAccount,"Web"));
        model.addAttribute("title", "Tài Khoản");
        model.addAttribute("listAccount", this.accountInterface.searchAccounts(search));
        model.addAttribute("file_html", "/components/accountBody");
        model.addAttribute("component", "accountBody");
        return "index";
    }
}
