package com.app.api.controller.api;

import com.app.api.model.accountModel;
import com.app.api.model.commentModel;
import com.app.api.model.productModel;
import com.app.api.service.FileStorageService;
import com.app.api.service._interface.commentInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/")
public class commentController {

    @Autowired
    private commentInterface commentInterface;
    @Autowired
    private com.app.api.service.tokenService tokenService;
    @Autowired
    private FileStorageService fileStorageService;

    @GetMapping("comment-product/{idProduct}")
    public List<commentModel> listCommentOfProduct(@PathVariable("idProduct") int idProduct) {
        return this.commentInterface.listCommentOfProduct(idProduct);
    }

    @PostMapping("add-comment")
    public ResponseEntity<?> addComment(@RequestHeader("Authorization") String authorizationHeader,
                                        @RequestParam("idProduct") int idProduct,
                                        @RequestParam("comment") String comment,
                                        @RequestParam("star") int star,
                                        @RequestParam("file") MultipartFile file) {
        String token = authorizationHeader.replace("Bearer ", "");
        int idAccount = Integer.parseInt(this.tokenService.validateTokenAndGetAccountId(token));

        accountModel accountModel = new accountModel();
        accountModel.setId(idAccount);

        productModel productModel = new productModel();
        productModel.setId(idProduct);

        commentModel newComment = new commentModel();
        newComment.setAccountModel(accountModel);
        newComment.setProductModel(productModel);
        newComment.setComment(comment);
        newComment.setStar(star);
        newComment.setImage(this.fileStorageService.storeFile(file));

        return  this.commentInterface.addComment(newComment)?
                ResponseEntity.status(200).body("ok"):
                ResponseEntity.status(500).body("Error");
    }

}
