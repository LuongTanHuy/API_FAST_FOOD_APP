package com.app.api.controller.api;

import com.app.api.service._interface.orderInterface;
import com.app.api.service.tokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/")
public class orderController {

    @Autowired
    private orderInterface orderInterface;
    @Autowired
    private tokenService tokenService;

    public static final int PRODUCT_IS_AWAITING_ACCEPT = 1;
    public static final int PRODUCT_IS_SHIPPING = 2;
    public static final int PRODUCT_IS_DELIVERED = 3;
    public static final int PRODUCT_CANCELED = 4;


    @PostMapping("add-order")
    public ResponseEntity<?> add(@RequestBody Map<String, Integer> requestData,
                                 @RequestHeader("Authorization") String authorizationHeader) {
        String idAccount = this.tokenService.validateTokenAndGetAccountId(authorizationHeader.replace("Bearer ", ""));
        int idStore = requestData.get("idStore");

        return ResponseEntity.status(200).body(this.orderInterface.addOrder(Integer.parseInt(idAccount), idStore));
    }

    @PutMapping("update-status-order")
    public ResponseEntity<?> updateStatus(@RequestBody Map<String,Object> requestData,
                                          @RequestHeader("Authorization") String authorizationHeader) {
        int STATUS = (int) requestData.get("status");

        switch (STATUS){
            case PRODUCT_IS_AWAITING_ACCEPT :
                List<Integer> id_order = (List<Integer>)requestData.get("idOrder");
                return  this.orderInterface.updateStatusListOrderWhenBuy(id_order, PRODUCT_IS_AWAITING_ACCEPT)?
                        ResponseEntity.status(200).body("Ok"):
                        ResponseEntity.status(500).body("Error");

            case PRODUCT_IS_SHIPPING, PRODUCT_IS_DELIVERED, PRODUCT_CANCELED:
                int idAccount = Integer.parseInt(this.tokenService.validateTokenAndGetAccountId(authorizationHeader.replace("Bearer ", "")));
                int status = (int) requestData.get("status");
                int idOrder = (int) requestData.get("idOrder");

                System.out.println("idAccount: " +idAccount);
                System.out.println("status: " +status);
                System.out.println("idOrder: " +idOrder);


                return  this.orderInterface.updateStatusOrderWhenDeliveryComplete(idOrder, status, idAccount) ?
                        ResponseEntity.status(200).body("Ok") :
                        ResponseEntity.status(500).body("Error");
        }
        return ResponseEntity.status(500).body("Error");
    }

}
