package com.app.api.service;

import com.app.api.model.accountModel;
import com.app.api.model.storeModel;
import com.app.api.repository._interface.accountRepository;
import com.app.api.repository._interface.orderRepository;
import com.app.api.repository._interface.storeRepository;
import com.app.api.service._interface.accountInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class accountService implements accountInterface {
    private final accountRepository accountRepository;
    private final storeRepository storeRepository;
    private final orderRepository  orderRepository;
    public static final int LOCK_ACCOUNT = 1;
    public static final int UNLOCK_ACCOUNT = 0;
    public static final String OPEN_STORE = "1";
    public static final String DELETE_STORE = "2";
    public static final int ORDERBOUGHT = 3;

    @Autowired
    public accountService(accountRepository accountRepository, storeRepository storeRepository, orderRepository orderRepository) {
        this.accountRepository = accountRepository;
        this.storeRepository = storeRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public boolean updateOtp(accountModel _account) {
        Optional<accountModel> account = this.accountRepository.findById(_account.getId());

        if(account.isPresent()){
            accountModel update_account = account.get();
            update_account.setOtp(_account.getOtp());

            return this.accountRepository.save(update_account).getId() > 0;
        }
        return false;
    }

    @Override
    @Transactional
    public boolean updateIdStore(int idStore,int idAccount) {
        Optional<accountModel> account = accountRepository.findById(idAccount);
        if (account.isPresent()) {
            accountModel updateAccount = account.get();
            storeModel storeModel = new storeModel();
            storeModel.setId(idStore);
            updateAccount.setStoreModel(storeModel);

            return this.accountRepository.save(updateAccount).getId() > 0;
        }
        return false;
    }


    @Override
    @Transactional
    public boolean updateProfile(accountModel _account) {
        Optional<accountModel> account = accountRepository.findById(_account.getId());
        if (account.isPresent()) {
            accountModel updateAccount = account.get();

            if (_account.getUsername() != null && !_account.getUsername().isEmpty()) {
                updateAccount.setUsername(_account.getUsername());
            }
            if (_account.getEmail() != null && !_account.getEmail().isEmpty()) {
                updateAccount.setEmail(_account.getEmail());
            }
            if (_account.getPhone() != null && !_account.getPhone().isEmpty()) {
                updateAccount.setPhone(_account.getPhone());
            }
            if (_account.getAddress() != null && !_account.getAddress().isEmpty()) {
                updateAccount.setAddress(_account.getAddress());
            }
            if (_account.getImage() != null && !_account.getImage().isEmpty()) {
                updateAccount.setImage(_account.getImage());
            }

            return this.accountRepository.save(updateAccount).getId() > 0;

        }
        return false;
    }

    @Override
    public boolean agreeOpenStore(int idAccount) {
        try {
            Optional<accountModel> account = this.accountRepository.findById(idAccount);
            if (account.isPresent()) {
                accountModel updateAccount = account.get();
                updateAccount.setPermission(OPEN_STORE);

                storeModel store = updateAccount.getStoreModel();
                if (store != null) {
                    Optional<storeModel> getStoreModel = this.storeRepository.findById(store.getId());
                    if (getStoreModel.isPresent()) {
                        storeModel updateStore = getStoreModel.get();
                        updateStore.setStatus(Integer.parseInt(OPEN_STORE));

                        this.storeRepository.save(updateStore);
                        this.accountRepository.save(updateAccount);
                        return true;
                    }
                }
            }
            return false;
        } catch (NullPointerException e) {
            System.err.println(STR."NullPointerException in agreeOpenStore: \{e.getMessage()}");
        } catch (Exception e) {
            System.err.println(STR."Exception in agreeOpenStore: \{e.getMessage()}");
        }
        return false;
    }

    @Override
    public boolean NotAgreeOpenStore(int idAccount) {
        try {
            Optional<accountModel> getAccount = this.accountRepository.findById(idAccount);
            if (getAccount.isPresent()) {
                accountModel updateAccount = getAccount.get();

                storeModel store = updateAccount.getStoreModel();
                if (store != null) {
                    Optional<storeModel> getStoreModel = this.storeRepository.findById(store.getId());
                    if (getStoreModel.isPresent()) {
                        storeModel updateStore = getStoreModel.get();
                        updateStore.setStatus(Integer.parseInt(DELETE_STORE));

                        this.storeRepository.save(updateStore);
                        return true;
                    }
                }
            }
            return false;
        } catch (NullPointerException e) {
            // Ghi lại lỗi nếu cần thiết
            System.err.println(STR."NullPointerException in NotAgreeOpenStore: \{e.getMessage()}");
        } catch (Exception e) {
            // Ghi lại lỗi nếu cần thiết
            System.err.println(STR."Exception in NotAgreeOpenStore: \{e.getMessage()}");
        }
        return false;
    }

    @Override
    public List<accountModel> getAllRequestOpenStore() {
        return this.accountRepository.getAllByStatusStore();
    }

    @Override
    public boolean deleteAccount(int idAccount) {
        this.accountRepository.deleteById(idAccount);
        return true;
    }

    @Override
    public accountModel accountProfile(int id,String WebOrApp) {
        accountModel accountProfile = new accountModel();

        accountProfile.setId(this.accountRepository.findById(id).get().getId());
        accountProfile.setUsername(this.accountRepository.findById(id).get().getUsername());
        accountProfile.setEmail(this.accountRepository.findById(id).get().getEmail());
        accountProfile.setAddress(this.accountRepository.findById(id).get().getAddress());
        accountProfile.setStatus(this.accountRepository.findById(id).get().getStatus());
        accountProfile.setImage(this.accountRepository.findById(id).get().getImage());
        accountProfile.setPhone(this.accountRepository.findById(id).get().getPhone());
        accountProfile.setPermission(this.accountRepository.findById(id).get().getPermission());
        accountProfile.setCreated_at(this.accountRepository.findById(id).get().getCreated_at());

        if (WebOrApp.equals("Web")) {accountProfile.setStoreModel(this.accountRepository.findById(id).get().getStoreModel());}

        return accountProfile;
    }

    @Override
    public accountModel getAccountDetailForWeb(int id) {
        accountModel accountModel = new accountModel();
        accountModel.setUsername(this.accountRepository.findById(id).get().getUsername());
        accountModel.setEmail(this.accountRepository.findById(id).get().getEmail());
        accountModel.setAddress(this.accountRepository.findById(id).get().getAddress());
        accountModel.setStatus(this.accountRepository.findById(id).get().getStatus());
        accountModel.setImage(this.accountRepository.findById(id).get().getImage());
        accountModel.setPhone(this.accountRepository.findById(id).get().getPhone());
        accountModel.setPermission(this.accountRepository.findById(id).get().getPermission());
        accountModel.setCreated_at(this.accountRepository.findById(id).get().getCreated_at());
        accountModel.setStoreModel(this.accountRepository.findById(id).get().getStoreModel());
        return accountModel;
    }

    @Override
    public List<accountModel> pageAccounts(Pageable pageable) {

        Page<accountModel> pageAccounts = this.accountRepository.findAll(pageable);
        List<accountModel> before_ListAccounts= pageAccounts.toList();
        List<accountModel> after_ListAccounts = new ArrayList<>();

        for(int i = 0;i< before_ListAccounts.size();i++){
             int totalOrderDelivered = this.orderRepository.totalOrderDelivered(before_ListAccounts.get(i).getId());
             int totalOrderBought = this.orderRepository.totalOrderBought(before_ListAccounts.get(i).getId(),ORDERBOUGHT);

            accountModel accountModel = new accountModel();
            accountModel.setUsername(before_ListAccounts.get(i).getUsername());
            accountModel.setEmail(before_ListAccounts.get(i).getEmail());
            accountModel.setAddress(before_ListAccounts.get(i).getAddress());
            accountModel.setStatus(before_ListAccounts.get(i).getStatus());
            accountModel.setImage(before_ListAccounts.get(i).getImage());
            accountModel.setPhone(before_ListAccounts.get(i).getPhone());
            accountModel.setPermission(before_ListAccounts.get(i).getPermission());
            accountModel.setCreated_at(before_ListAccounts.get(i).getCreated_at());
            accountModel.setTotalOrderDelivered(totalOrderDelivered > 0 ? totalOrderDelivered : 0);
            accountModel.setTotalOrderBought(totalOrderBought > 0 ? totalOrderBought : 0);

             after_ListAccounts.add(i,accountModel);
        }

        return after_ListAccounts;
    }

    @Override
    public int totalAccount(){
        return this.accountRepository.findAll().size();
    }

    @Override
    public List<accountModel> searchAccounts(String information){
        return this.accountRepository.searchAccounts(information);
    }
    @Override
    public boolean lockAccount(int idAccount) {
        Optional<accountModel> optionalAccount = this.accountRepository.findById(idAccount);
        if (optionalAccount.isPresent()) {
            accountModel account = optionalAccount.get();
            int currentStatus = account.getStatus();
            int newStatus = (currentStatus == UNLOCK_ACCOUNT) ? LOCK_ACCOUNT : UNLOCK_ACCOUNT;
            account.setStatus(newStatus);

            return this.accountRepository.save(account).getId() > 0;
        }
        return false;
    }

    @Override
    public boolean checkEmail(String Email){
        return this.accountRepository.findByEmail(Email).size() > 0 ? true:false;
    }

    @Override
    public boolean checkOtp(int idAccount, String otp) {
        if (this.accountRepository.findById(idAccount).get().getId() > 0 && this.accountRepository.findById(idAccount).get().getOtp().equals(otp)) {
                Optional<accountModel> account = this.accountRepository.findById(idAccount);
                if (account.isPresent()) {
                    accountModel update_account = account.get();
                    update_account.setStatus(1);
                    this.accountRepository.save(update_account);
                    return true;
                }
        }else{
            this.accountRepository.deleteById(idAccount);
            return false;
        }
        return false;
    }

}
