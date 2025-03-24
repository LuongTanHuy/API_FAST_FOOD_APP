package com.app.api.controller.api;

import com.app.api.model.orderItemModel;
import com.app.api.service._interface.orderItemInterface;
import com.app.api.service.tokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/")
public class orderItemController {
    @Autowired
    private orderItemInterface orderItemInterface;

    @Autowired
    private tokenService tokenService;

    @PostMapping("add-order-item")
    public ResponseEntity<?> add(@RequestBody Map<String, Object> requestData) {
        try {
            int idOrder   = Integer.parseInt(requestData.get("idOrder").toString());
            int idProduct = Integer.parseInt(requestData.get("idProduct").toString());
            int quantity  = Integer.parseInt(requestData.get("quantity").toString());

            return this.orderItemInterface.add(idOrder, idProduct, quantity) > 0 ?
                    ResponseEntity.status(200).body("ok") :
                    ResponseEntity.status(500).body("Error");

        } catch (NumberFormatException e) {
            System.out.println("add-order-item: "+e.getMessage());
            return ResponseEntity.status(400).body("Invalid data format");
        }
    }


    @GetMapping("order-item")
    public List<orderItemModel> listOrderItem(@RequestHeader("Authorization") String authorizationHeader,
                                              @RequestParam("statusOrderItem") int statusOrderItem) {
        try {
            int accountId = Integer.parseInt(this.tokenService.validateTokenAndGetAccountId(authorizationHeader.replace("Bearer ", "")));
            return this.orderItemInterface.listOrderItem(accountId, statusOrderItem);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    @GetMapping("order-for-shipper")
    public List<orderItemModel> listOrderForShipper(@RequestParam("statusOrderItem") int statusOrderItem) {
        return this.orderItemInterface.listOrderForShipper(statusOrderItem);
    }

    @GetMapping("order-of-shipper")
    public List<orderItemModel> listOrderOfShipper(@RequestHeader("Authorization") String authorizationHeader,
                                                    @RequestParam("statusOrderItem") int statusOrderItem) {
        int accountId = Integer.parseInt(this.tokenService.validateTokenAndGetAccountId(authorizationHeader.replace("Bearer ", "")));
        return this.orderItemInterface.listOrderOfShipper(statusOrderItem,accountId);
    }

    @PutMapping("update-quantity-for-order-item")
    public ResponseEntity<?> updateQuantityForOrderItem(@RequestBody orderItemModel orderItemModel){
        return  this.orderItemInterface.updateQuantityForOrderItem(orderItemModel)?
                ResponseEntity.status(200).body("ok"):
                ResponseEntity.status(500).body("Error");
    }

    @PutMapping("update-price-for-order-item")
    public ResponseEntity<?> updatePriceForOrderItem(@RequestBody Map<String, Object> requestData) {
        List<Integer> idOrder = (List<Integer>)requestData.get("idOrder");

        return  this.orderItemInterface.updatePriceForOrderItem(idOrder)?
                ResponseEntity.status(200).body("Ok"):
                ResponseEntity.status(500).body("Error");
    }
}
