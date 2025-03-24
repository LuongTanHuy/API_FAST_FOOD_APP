package com.app.api.service;

import com.app.api.model.categoryModel;
import com.app.api.model.storeModel;
import com.app.api.repository._interface.categoryRepository;
import com.app.api.repository._interface.orderItemRepository;
import com.app.api.repository._interface.storeRepository;
import com.app.api.service._interface.storeInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class storeService implements storeInterface {
    @Autowired
    private storeRepository storeRepository;
    @Autowired
    private categoryRepository categoryRepository;
    @Autowired
    private orderItemRepository orderItemRepository;
    private static final int FINISH = 3;

    private List<String> convertListCategory(List<categoryModel> listCategory) {
        List<String> result = new ArrayList<>();

        for (int i = 0; i < listCategory.size(); i++) {
            result.add(listCategory.get(i).getCategory());
        }
        return result;
    }

    private int totalOrdersSold(int idStore) {
        return this.orderItemRepository.listOderItemOfStore(idStore,FINISH).size();
    }

    private double revenue(int idStore) {
        DecimalFormat decimalFormat = new DecimalFormat("#,000");
        double result = 0;
        for(int i=0 ;i< this.orderItemRepository.listOderItemOfStore(idStore,FINISH).size();i++){
            result += this.orderItemRepository.listOderItemOfStore(idStore,FINISH).get(i).getPrice();
        }
        return Double.parseDouble(decimalFormat.format(result));
    }

    @Override
    public List<storeModel> listStore(Pageable pageable) {
        Page<storeModel> listStore = this.storeRepository.findAll(pageable);
        List<storeModel> containerListStore = listStore.toList();
        List<storeModel> resultListStore = new ArrayList<>();

        for(int i=0;i< containerListStore.size();i++){
            int idStore = containerListStore.get(i).getId();
            List<categoryModel> listCategory = this.categoryRepository.getAllByIdStore(idStore);

            storeModel object = containerListStore.get(i);
            object.setListCategory(this.convertListCategory(listCategory));
            object.setTotalOrdersSold(this.totalOrdersSold(idStore));
            object.setRevenue(this.revenue(idStore));

            resultListStore.add(object);

        }
        return resultListStore;
    }

    @Override
    public List<storeModel> getAllRequestOpenStore() {
        return this.storeRepository.getAllRequestOpenStore();
    }

    @Override
    public int totalStore(){
        return this.storeRepository.findAll().size();
    }

    @Override
    public List<storeModel> search(String search) {
        return this.storeRepository.findByKeyword(search);
    }

    @Override
    public boolean updateInfo(storeModel storeModel) {
        Optional<storeModel> getStoreModel = this.storeRepository.findById(storeModel.getId());
        if (getStoreModel.isPresent()) {
            storeModel updateStoreModel = getStoreModel.get();
            updateStoreModel.setName(storeModel.getName());
            updateStoreModel.setAddress(storeModel.getAddress());
            updateStoreModel.setEmail(storeModel.getEmail());
            updateStoreModel.setPhone(storeModel.getPhone());

            if (storeModel.getImage() != null && !storeModel.getImage().isEmpty()) {
                updateStoreModel.setImage(storeModel.getImage());
            }

            this.storeRepository.save(updateStoreModel);
            return true;
        }
        return false;
    }

    @Override
    public int addStore(storeModel storeModel) {
        return this.storeRepository.save(storeModel).getId();
    }

}
