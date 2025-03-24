package com.app.api.service;

import com.app.api.model.Customer;
import com.app.api.model.HistoryChat;
import com.app.api.model.chatModel;
import com.app.api.repository._interface.chatRepository;
import com.app.api.service._interface.chatInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class chatService implements chatInterface {

    @Autowired
    private chatRepository chatRepository;

    @Override
    public boolean addChat(chatModel chatModel) {
        return this.chatRepository.save(chatModel).getId() > 0 ?true:false;
    }

    @Override
    public List<Customer> listCustomer(int id_store) {
        List<Customer> customers = this.chatRepository.listCustomer(id_store);
        Map<String, Customer> uniqueCustomers = new HashMap<>();

        for (Customer customer : customers) {
            uniqueCustomers.putIfAbsent(String.valueOf(customer.getIdAccount()), customer);
        }
        return new ArrayList<>(uniqueCustomers.values());
    }

    @Override
    public List<HistoryChat> historyChat(int idAccount, int idStore) {
        return this.chatRepository.historyChat(idAccount,idStore);
    }


}
