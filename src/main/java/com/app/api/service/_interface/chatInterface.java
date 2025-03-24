package com.app.api.service._interface;

import com.app.api.model.Customer;
import com.app.api.model.HistoryChat;
import com.app.api.model.chatModel;

import java.util.List;

public interface chatInterface {
    public boolean addChat(chatModel chatModel);
    public List<Customer> listCustomer(int id_store);
    List<HistoryChat> historyChat(int idAccount, int idStore);

}
