package com.app.api.repository._interface;

import com.app.api.model.bannerModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface bannerRepository extends JpaRepository<bannerModel,Integer> {
    @Query("SELECT a FROM bannerModel a WHERE a.text LIKE %:keyword% ")
    List<bannerModel> findByKeyword(@Param("keyword") String keyword);
}
