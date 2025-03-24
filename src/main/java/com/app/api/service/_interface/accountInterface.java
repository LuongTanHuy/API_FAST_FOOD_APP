package com.app.api.service._interface;

import com.app.api.model.accountModel;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface accountInterface {

    public boolean updateOtp(accountModel _account);
    public boolean updateProfile(accountModel _account);
    public boolean agreeOpenStore(int idAccount);
    public boolean NotAgreeOpenStore(int idAccount);
    public boolean lockAccount(int id);
    public boolean updateIdStore(int idStore,int idAccount);
    public boolean checkEmail(String Email);
    public boolean checkOtp(int idAccount, String otp);
    public int totalAccount();
    public boolean deleteAccount(int id);
    public List<accountModel> getAllRequestOpenStore();
    public List<accountModel> pageAccounts(Pageable pageable);
    public List<accountModel> searchAccounts(String value);
    public accountModel accountProfile(int id,String WebOrApp);
    public accountModel getAccountDetailForWeb(int id);

}
