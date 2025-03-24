package com.app.api.service._interface;

import com.app.api.model.commentModel;

import java.util.List;

public interface commentInterface {
    public boolean addComment(commentModel commentModel);
    public List<commentModel> listCommentOfProduct(int id_product);
}
