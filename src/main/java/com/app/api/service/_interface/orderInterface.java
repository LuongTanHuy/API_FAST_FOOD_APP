package com.app.api.service._interface;

import java.util.List;

public interface orderInterface {
    public int addOrder(int idAccount,int idStore);
    public boolean updateStatusListOrderWhenBuy(List<Integer> id_order, int status);
    public boolean updateStatus(int id_order, int status);
    public boolean updateStatusOrderWhenDeliveryComplete(int id_order, int status,int idShipper);
}
