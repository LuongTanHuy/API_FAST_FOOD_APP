package com.app.api.service;

import com.app.api.model.bannerModel;
import com.app.api.repository._interface.bannerRepository;
import com.app.api.service._interface.bannerInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class bannerService implements bannerInterface
{
    @Autowired
    private bannerRepository bannerRepository;


    @Override
    public List<bannerModel> getAll() {
        return this.bannerRepository.findAll();
    }

    @Override
    public boolean addBanner(bannerModel bannerModel) {
        if(this.bannerRepository.save(bannerModel).getId() > 0){
            return true;
        }
        return false;
    }

    @Override
    public boolean updateBanner(bannerModel bannerModel) {
        Optional<bannerModel> optionalBannerModel = this.bannerRepository.findById(bannerModel.getId());

        if (optionalBannerModel.isPresent()) {
            bannerModel updateBannerModel = optionalBannerModel.get();

            if (bannerModel.getImage() != null ) {
                updateBannerModel.setImage(bannerModel.getImage());
            }

            if (bannerModel.getText() != null ) {
                updateBannerModel.setText(bannerModel.getText());
            }
            this.bannerRepository.save(updateBannerModel);

            return true;
        }

        return false;
    }

    @Override
    public boolean deleteBanner(int id) {
        this.bannerRepository.deleteById(id);
        return true;
    }

    @Override
    public List<bannerModel> search(String search) {
        return this.bannerRepository.findByKeyword(search);
    }
}
