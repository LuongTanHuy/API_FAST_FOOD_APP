package com.app.api.service._interface;

import com.app.api.model.orderItemModel;

import java.util.List;

public interface orderItemInterface {

    public boolean updatePriceForOrderItem(List<Integer> id_order);
    public boolean updateQuantityForOrderItem(orderItemModel orderItemModel);

    public List<orderItemModel> listOrderItem(int id,int status);
    public List<orderItemModel> listOrderForShipper(int statusOrderItem);
    public List<orderItemModel> listOderItemOfStore(int id_store,int status);
    public List<orderItemModel> listOrderOfShipper(int status,int idShipper);

    public int[] getQuantityOrder(int idStore);
    public int add(int id_order, int id_product,int quantity);
    public double[] getPriceOrder(int idStore,int year );

}
