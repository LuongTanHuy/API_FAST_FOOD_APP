package com.app.api.repository._interface;

import com.app.api.model.categoryModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface categoryRepository extends JpaRepository<categoryModel,Integer> {

    @Query("SELECT a FROM categoryModel a WHERE  a.storeModel.id =:id_store ")
    List<categoryModel> getAllByIdStore(@Param("id_store") int id_store);


}
