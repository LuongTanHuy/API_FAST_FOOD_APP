package com.app.api.service;

import com.app.api.model.accountModel;
import com.app.api.model.orderModel;
import com.app.api.model.storeModel;
import com.app.api.repository._interface.orderRepository;
import com.app.api.service._interface.orderInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class orderService implements orderInterface {

    private final orderRepository orderRepository;
    private emailService emailService;

    @Autowired
    public orderService(orderRepository orderRepository, emailService emailService) {
        this.orderRepository = orderRepository;
        this.emailService = emailService;
    }

    @Override
    public int addOrder(int idAccount, int idStore) {
            orderModel orderModel = new orderModel();

            accountModel accountModel = new accountModel();
            accountModel.setId(idAccount);

            storeModel storeModel = new storeModel();
            storeModel.setId(idStore);

            orderModel.setAccountModel(accountModel);
            orderModel.setStoreModel(storeModel);
            return this.orderRepository.save(orderModel).getId();
    }

    @Override
    public boolean updateStatusListOrderWhenBuy(List<Integer> id_order, int status) {
        for(int i= 0 ;i < id_order.size();i++){
            Optional<orderModel> orderModel = this.orderRepository.findById(id_order.get(i));
            if (orderModel.isPresent()) {
                orderModel updateOrderModel = orderModel.get();
                updateOrderModel.setStatus(status);
                this.orderRepository.save(updateOrderModel);
            }
        }
        return true;
    }

    @Override
    public boolean updateStatus(int id_order, int status) {
        Optional<orderModel> orderModel = this.orderRepository.findById(id_order);
        if (orderModel.isPresent()) {
            orderModel updateOrderModel = orderModel.get();
            updateOrderModel.setStatus(status);
            this.orderRepository.save(updateOrderModel);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateStatusOrderWhenDeliveryComplete(int id_order, int status, int idShipper) {
        Optional<orderModel> orderModel = this.orderRepository.findById(id_order);
        if (orderModel.isPresent()) {

            orderModel updateOrderModel = orderModel.get();
            updateOrderModel.setStatus(status);
            updateOrderModel.setId_Shipper(idShipper);
            this.orderRepository.save(updateOrderModel);

            if(status == 3){
                emailService.sendNotificationWhenPayment(updateOrderModel);
            }

            return true;
        }
        return false;
    }

}
