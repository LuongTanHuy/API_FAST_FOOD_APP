package com.app.api.repository._interface;

import com.app.api.model.orderItemModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface orderItemRepository extends JpaRepository<orderItemModel,Integer> {

    @Query("SELECT oi FROM orderItemModel oi INNER JOIN oi.orderModel o WHERE o.accountModel.id = ?1 AND oi.orderModel.status = ?2")
    List<orderItemModel> listOrderItem(int accountId,int status);

    @Query("SELECT oi FROM orderItemModel oi INNER JOIN oi.orderModel o WHERE o.storeModel.id = ?1 AND oi.orderModel.status = ?2 ORDER BY oi.orderModel.created_at,oi.orderModel.receive_at DESC ")
    List<orderItemModel> listOderItemOfStore(int storeId,int status);

    @Query("SELECT oi FROM orderItemModel oi  WHERE  oi.orderModel.status = ?1 AND oi.orderModel.id_Shipper = 0 ")
    List<orderItemModel> listOrderForShipper(int status);

    @Query("SELECT oi FROM orderItemModel oi  WHERE  oi.orderModel.status = ?1 AND oi.orderModel.id_Shipper = ?2")
    List<orderItemModel> listOrderOfShipper(int status,int idShipper);

    @Query("SELECT oi FROM orderItemModel oi INNER JOIN oi.orderModel o WHERE o.storeModel.id = ?1 AND oi.orderModel.status = ?2 AND MONTH(oi.orderModel.receive_at) = ?3 AND YEAR(oi.orderModel.receive_at) = ?4")
    List<orderItemModel> findAllByYear(int storeId,int status,int month,int year);

    @Query("SELECT oi FROM orderItemModel oi  WHERE  oi.orderModel.id = ?1")
    Optional<orderItemModel> findByIdOrder(int id);

    @Query("SELECT SUM (oi.quantity) FROM orderItemModel oi  WHERE oi.productModel.id = ?1 AND oi.orderModel.status = 3 ")
    Integer totalProductSold(int idProduct);

    @Query("SELECT SUM (oi.price) FROM orderItemModel oi  WHERE oi.productModel.id = ?1 AND oi.orderModel.status = 3 ")
    Double totalRevenue(int idProduct);
}
