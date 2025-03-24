package com.app.api.service._interface;

import com.app.api.model.storeModel;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface storeInterface {
    public int totalStore();
    public  List<storeModel> listStore(Pageable pageable);
    public List<storeModel> getAllRequestOpenStore();
    public List<storeModel> search(String search);
    public boolean updateInfo(storeModel storeModel);
    public int addStore(storeModel storeModel);

}
