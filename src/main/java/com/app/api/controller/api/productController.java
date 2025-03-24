package com.app.api.controller.api;

import com.app.api.model.categoryModel;
import com.app.api.model.productModel;
import com.app.api.service._interface.productInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/")
public class productController {

    @Autowired
    private productInterface productInterface;

    @GetMapping("product")
    public List<productModel> listProducts(){
        return this.productInterface.listProducts();
    }

    @GetMapping("product-detail/{idProduct}")
    public productModel getProductDetail(@PathVariable("idProduct") int idProduct){
        return this.productInterface.getProductDetail(idProduct);
    }

    @GetMapping("product-of-the-same-type/{idCategory}")
    public ResponseEntity<List<productModel>> productOfTheSameType(@PathVariable("idCategory") int idCategory){
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(this.productInterface.productOfTheSameType(idCategory));
    }

    @GetMapping("category-of-shop-api/{idStore}")
    public List<categoryModel> categoryOfShop(@PathVariable("idStore") int idStore){
        return this.productInterface.categoryOfShop(idStore);
    }

    @GetMapping("product-of-shop-api/{idStore}")
    public List<productModel> productOfShop(@PathVariable("idStore") int idStore){
        return this.productInterface.productOfShop(idStore);
    }
}
