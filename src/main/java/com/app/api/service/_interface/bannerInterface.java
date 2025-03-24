package com.app.api.service._interface;

import com.app.api.model.bannerModel;

import java.util.List;

public interface bannerInterface {
    public List<bannerModel> getAll();
    public boolean addBanner(bannerModel bannerModel);
    public boolean updateBanner(bannerModel bannerModel);
    public boolean deleteBanner(int id);
    public List<bannerModel> search(String search);
}
