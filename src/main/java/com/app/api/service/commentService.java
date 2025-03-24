package com.app.api.service;

import com.app.api.model.accountModel;
import com.app.api.model.commentModel;
import com.app.api.repository._interface.commentRepository;
import com.app.api.service._interface.commentInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class commentService implements commentInterface {

    @Autowired
    private commentRepository commentRepository;
    @Override
    public boolean addComment(commentModel commentModel) {
        if(this.commentRepository.save(commentModel).getId() > 0){
            return true;
        }
        return false;
    }

    @Override
    public List<commentModel> listCommentOfProduct(int id_product) {
        List<commentModel> commentModel = this.commentRepository.findAll();
        List<commentModel> result = new ArrayList<>();
        for(int i=0;i<commentModel.size();i++){
            if(commentModel.get(i).getProductModel().getId() == id_product){
                commentModel getModel = new commentModel();
                getModel.setComment(commentModel.get(i).getComment());
                getModel.setImage(commentModel.get(i).getImage());
                getModel.setStar(commentModel.get(i).getStar());
                getModel.setCreated_at(commentModel.get(i).getCreated_at());

                accountModel getAccount = new accountModel();
                getAccount.setUsername(commentModel.get(i).getAccountModel().getUsername());
                getAccount.setImage(commentModel.get(i).getAccountModel().getImage());

                getModel.setAccountModel(getAccount);
                result.add(getModel);
            }
        }
        return result;
    }
}
