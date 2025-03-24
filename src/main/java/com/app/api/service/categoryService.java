package com.app.api.service;

import com.app.api.model.categoryModel;
import com.app.api.model.storeModel;
import com.app.api.repository._interface.categoryRepository;
import com.app.api.service._interface.categoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class categoryService implements categoryInterface {
    @Autowired
    private categoryRepository categoryRepository;

    @Override
    public List<categoryModel> getAllByIdStore(int id_store) {
        List<categoryModel> listCategory = this.categoryRepository.getAllByIdStore(id_store);
        List<categoryModel> result_Category = new ArrayList<>();
        for(int i =0 ;i< listCategory.size();i++) {
            storeModel newStoreModel = new storeModel();
            newStoreModel.setId(listCategory.get(i).getStoreModel().getId());
            categoryModel newCategoryModel = new categoryModel();
            newCategoryModel.setId(listCategory.get(i).getId());
            newCategoryModel.setCategory(listCategory.get(i).getCategory());
            newCategoryModel.setStoreModel(newStoreModel);
            newCategoryModel.setStatus(listCategory.get(i).getStatus());
            newCategoryModel.setSale(listCategory.get(i).getSale());
            result_Category.add(newCategoryModel);
        }
        return result_Category;
    }

    @Override
    public boolean add(categoryModel categoryModel) {
        this.categoryRepository.save(categoryModel);
        return true;
    }

    @Override
    public boolean changeStatus(int id_category) {
        Optional<categoryModel> getCategoryModel = this.categoryRepository.findById(id_category);
        if(getCategoryModel.isPresent()){
            if(getCategoryModel.get().getStatus() == 0){
               categoryModel update = getCategoryModel.get();
               update.setStatus(1);
                this.categoryRepository.save(update);
            }else{
                categoryModel update = getCategoryModel.get();
                update.setStatus(0);
                this.categoryRepository.save(update);
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean update(categoryModel categoryModel) {
        Optional<categoryModel> getCategory = this.categoryRepository.findById(categoryModel.getId());
        if(getCategory.isPresent()){
            categoryModel update = getCategory.get();
            update.setCategory(categoryModel.getCategory());
            update.setSale(categoryModel.getSale());
            this.categoryRepository.save(update);
            return true;
        }
        return false;
    }


}
