package com.app.api.repository._interface;

import com.app.api.model.productModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface productRepository extends JpaRepository<productModel,Integer> {
    @Query("SELECT a FROM productModel a WHERE a.name LIKE %:keyword% AND a.categoryModel.storeModel.id =:idStore ORDER BY a.categoryModel.category desc")
    List<productModel> searchProductOfShop(@Param("idStore") int idStore,@Param("keyword") String keyword);

    @Query("SELECT a FROM productModel a WHERE a.categoryModel.storeModel.id = :idStore ORDER BY a.categoryModel.category desc")
    List<productModel> productOfShop(@Param("idStore") int idStore);

    @Query("SELECT a FROM productModel a WHERE a.categoryModel.id = :idCategory ORDER BY a.categoryModel.category desc ")
    List<productModel> productOfTheSaneType(@Param("idCategory") int idCategory);

    @Query("SELECT a FROM productModel a WHERE a.categoryModel.storeModel.id = :idStore AND a.categoryModel.id = :idCategory ORDER BY a.categoryModel.category desc")
    List<productModel> searchProductOfTheSameType(@Param("idStore") int idStore,@Param("idCategory") int idCategory);

    @Query("SELECT a FROM productModel a ORDER BY a.categoryModel.category desc")
    List<productModel> getProductSale();
}
