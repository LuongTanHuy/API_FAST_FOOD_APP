package com.app.api.repository._interface;

import com.app.api.model.orderModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface orderRepository extends JpaRepository<orderModel,Integer> {
    @Query("SELECT COUNT(*) AS totalOrder FROM orderModel o WHERE o.id_Shipper = ?1 ")
    int totalOrderDelivered(int accountId);

    @Query("SELECT COUNT(*) AS totalOrderBought FROM orderModel o WHERE o.accountModel.id = ?1 AND o.status = ?2")
    int totalOrderBought(int accountId, int status);
}
