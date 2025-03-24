package com.app.api.service._interface;

import com.app.api.model.categoryModel;

import java.util.List;

public interface categoryInterface {
    public List<categoryModel> getAllByIdStore(int id_store);
    public boolean add(categoryModel categoryModel);
    public boolean changeStatus(int id_category);
    public boolean update(categoryModel categoryModel);
}
