package com.app.api.repository._interface;

import com.app.api.model.accountModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface accountRepository extends JpaRepository<accountModel,Integer> {
    @Query("SELECT a FROM accountModel a WHERE a.username LIKE %:keyword% OR a.email LIKE %:keyword% OR a.phone LIKE %:keyword%")
    List<accountModel> searchAccounts(@Param("keyword") String keyword);
    @Query("SELECT a FROM accountModel a WHERE  a.email =:keyword ")
    List<accountModel> findByEmail(@Param("keyword") String keyword);
    @Query("SELECT a FROM accountModel a ORDER BY a.created_at DESC ")
    List<accountModel> listAccountByTime();

    @Query("SELECT a FROM accountModel a WHERE a.storeModel.status = 2 ORDER BY a.created_at DESC ")
    List<accountModel> getAllByStatusStore();
}
