package com.app.api.repository._interface;

import com.app.api.model.storeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface storeRepository extends JpaRepository<storeModel,Integer> {
    @Query("SELECT a FROM storeModel a WHERE a.name LIKE %:keyword% OR a.email LIKE %:keyword% OR a.phone LIKE %:keyword% OR a.address LIKE %:keyword%")
    List<storeModel> findByKeyword(@Param("keyword") String keyword);
    @Query("SELECT a FROM storeModel a WHERE a.status < 2 ORDER BY a.created_at DESC ")
    List<storeModel> getStoreByTime();

    @Query("SELECT a FROM storeModel a WHERE a.status = 2 ORDER BY a.created_at DESC ")
    List<storeModel> getAllRequestOpenStore();

}
