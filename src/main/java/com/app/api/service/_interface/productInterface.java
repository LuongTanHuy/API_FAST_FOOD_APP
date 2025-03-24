package com.app.api.service._interface;

import com.app.api.model.categoryModel;
import com.app.api.model.productModel;

import java.util.List;

public interface productInterface {
    public List<productModel> listProducts();
    public List<productModel> productOfTheSameType(int id_category);
    public List<productModel> productOfShop(int id_store);
    public List<categoryModel> categoryOfShop(int id_store);
    public List<productModel> searchProductOfShop(int id_store,String search);
    public List<productModel> searchProductOfTheSameType(int id_store,int id_category);

    public productModel getProductDetail(int id);

    public boolean changeStatusProduct(int id);
    public boolean addProduct(productModel productModel);
    public boolean updateProduct(productModel productModel);
}
