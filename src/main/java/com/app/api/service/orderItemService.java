package com.app.api.service;

import com.app.api.model.*;
import com.app.api.repository._interface.orderItemRepository;
import com.app.api.repository._interface.orderRepository;
import com.app.api.repository._interface.productRepository;
import com.app.api.service._interface.orderItemInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class orderItemService implements orderItemInterface {

    private final orderItemRepository orderItemRepository;
    private final orderRepository orderRepository;
    private final productRepository productRepository;

    @Autowired
    public orderItemService(orderItemRepository orderItemRepository,
                            orderRepository orderRepository,
                            productRepository productRepository) {
        this.orderItemRepository = orderItemRepository;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    @Override
    public int add(int id_order, int id_product, int quantity) {
        // Kiểm tra xem orderModel và productModel có tồn tại không
        Optional<orderModel> optionalOrderModel = this.orderRepository.findById(id_order);
        Optional<productModel> optionalProductModel = this.productRepository.findById(id_product);

        if (!optionalOrderModel.isPresent()) {
            throw new IllegalArgumentException("Order with id " + id_order + " does not exist.");
        }
        if (!optionalProductModel.isPresent()) {
            throw new IllegalArgumentException("Product with id " + id_product + " does not exist.");
        }

        // Lấy các đối tượng đã tồn tại từ cơ sở dữ liệu
        orderModel orderModel = optionalOrderModel.get();
        productModel productModel = optionalProductModel.get();

        // Khởi tạo orderItemModel và thiết lập các giá trị
        orderItemModel orderItemModel = new orderItemModel();
        orderItemModel.setOrderModel(orderModel);
        orderItemModel.setProductModel(productModel);
        orderItemModel.setQuantity(quantity);

        // Lưu orderItemModel vào cơ sở dữ liệu và trả về ID
        return this.orderItemRepository.save(orderItemModel).getId();
    }

    @Override
    public boolean updatePriceForOrderItem(List<Integer> id_order) {
        for(int i= 0 ;i < id_order.size();i++){
            Optional<orderItemModel> orderItemModel = this.orderItemRepository.findByIdOrder(id_order.get(i));
            if (orderItemModel.isPresent()) {
                orderItemModel updateOrderIemModel = orderItemModel.get();
                double price = updateOrderIemModel.getProductModel().getPrice() - (updateOrderIemModel.getProductModel().getPrice()*updateOrderIemModel.getProductModel().getCategoryModel().getSale())/100;
                updateOrderIemModel.setPrice(price);
                this.orderItemRepository.save(updateOrderIemModel);
            }
        }
        return true;
    }

    @Override
    public List<orderItemModel> listOrderItem(int id_account,int status) {
        List<orderItemModel> listItem = this.orderItemRepository.listOrderItem(id_account,status);
        return this.informationOrderItem(listItem);
    }

    @Override
    public List<orderItemModel> listOrderForShipper(int statusOrderItem) {
        List<orderItemModel> listItem = this.orderItemRepository.listOrderForShipper(statusOrderItem);
        return this.informationOrderItem(listItem);
    }

    @Override
    public List<orderItemModel> listOderItemOfStore(int id_store, int status) {
        return this.orderItemRepository.listOderItemOfStore(id_store,status);
    }

    @Override
    public List<orderItemModel> listOrderOfShipper(int status, int idShipper) {
        List<orderItemModel> listItem = this.orderItemRepository.listOrderOfShipper(status,idShipper);
        return this.informationOrderItem(listItem);
    }

    private List<orderItemModel> informationOrderItem(List<orderItemModel> listItem){
        List<orderItemModel> listItemResult = new ArrayList<>();

        for(int i= 0;i<listItem.size();i++){
            orderItemModel orderItemModel =new orderItemModel();

            // Category
            categoryModel categoryModel = new categoryModel();
            categoryModel.setId(listItem.get(i).getProductModel().getCategoryModel().getId());
            categoryModel.setCategory(listItem.get(i).getProductModel().getCategoryModel().getCategory());
            categoryModel.setSale(listItem.get(i).getProductModel().getCategoryModel().getSale());

            // Product
            productModel productModel = new productModel();
            productModel.setId(listItem.get(i).getProductModel().getId());
            productModel.setImage(listItem.get(i).getProductModel().getImage());
            productModel.setName(listItem.get(i).getProductModel().getName());
            productModel.setPrice(listItem.get(i).getProductModel().getPrice());
            productModel.setCategoryModel(categoryModel);

            // Store
            storeModel storeModel = new storeModel();
            storeModel.setPhone(listItem.get(i).getOrderModel().getStoreModel().getPhone());
            storeModel.setAddress(listItem.get(i).getOrderModel().getStoreModel().getAddress());
            storeModel.setName(listItem.get(i).getOrderModel().getStoreModel().getName());

            //AccountModel
            accountModel accountModel = new accountModel();
            accountModel.setUsername(listItem.get(i).getOrderModel().getAccountModel().getUsername());
            accountModel.setAddress(listItem.get(i).getOrderModel().getAccountModel().getAddress());
            accountModel.setPhone(listItem.get(i).getOrderModel().getAccountModel().getPhone());

            //OrderModel
            orderModel orderModel = new orderModel();
            orderModel.setId(listItem.get(i).getOrderModel().getId());
            orderModel.setCreated_at(listItem.get(i).getOrderModel().getCreated_at());
            orderModel.setReceive_at(listItem.get(i).getOrderModel().getReceive_at());
            orderModel.setStoreModel(storeModel);
            orderModel.setAccountModel(accountModel);

            //OrderItemModel
            orderItemModel.setId(listItem.get(i).getId());
            orderItemModel.setQuantity(listItem.get(i).getQuantity());
            orderItemModel.setPrice(listItem.get(i).getPrice());
            orderItemModel.setOrderModel(orderModel);
            orderItemModel.setProductModel(productModel);
            listItemResult.add(i,orderItemModel);
        }

        return listItemResult;
    }

    @Override
    public int[] getQuantityOrder(int idStore) {

            int[] quantityOrder = new int[4];
            quantityOrder[0] = this.orderItemRepository.listOderItemOfStore(idStore,1).size();
            quantityOrder[1] = this.orderItemRepository.listOderItemOfStore(idStore,2).size();
            quantityOrder[2] = this.orderItemRepository.listOderItemOfStore(idStore,3).size();
            quantityOrder[3] = this.orderItemRepository.listOderItemOfStore(idStore,4).size();
            return quantityOrder;

    }

    public double price( List<orderItemModel> orderItemModel){
        double value = 0;
        if(!orderItemModel.isEmpty()){
            for(int i= 0;i < orderItemModel.size();i++){
                value += orderItemModel.get(i).getPrice();
            }
        }
        return value;
    }

    @Override
    public double[] getPriceOrder(int idStore, int year) {
        List<orderItemModel> month1 = this.orderItemRepository.findAllByYear(idStore,3,1,year);
        List<orderItemModel> month2 = this.orderItemRepository.findAllByYear(idStore,3,2,year);
        List<orderItemModel> month3 = this.orderItemRepository.findAllByYear(idStore,3,3,year);
        List<orderItemModel> month4 = this.orderItemRepository.findAllByYear(idStore,3,4,year);
        List<orderItemModel> month5 = this.orderItemRepository.findAllByYear(idStore,3,5,year);
        List<orderItemModel> month6 = this.orderItemRepository.findAllByYear(idStore,3,6,year);
        List<orderItemModel> month7 = this.orderItemRepository.findAllByYear(idStore,3,7,year);
        List<orderItemModel> month8 = this.orderItemRepository.findAllByYear(idStore,3,8,year);
        List<orderItemModel> month9 = this.orderItemRepository.findAllByYear(idStore,3,9,year);
        List<orderItemModel> month10 = this.orderItemRepository.findAllByYear(idStore,3,10,year);
        List<orderItemModel> month11 = this.orderItemRepository.findAllByYear(idStore,3,11,year);
        List<orderItemModel> month12 = this.orderItemRepository.findAllByYear(idStore,3,12,year);

        double[] priceOfYear = new double[12];
        priceOfYear[0] = this.price(month1);
        priceOfYear[1] = this.price(month2);
        priceOfYear[2] = this.price(month3);
        priceOfYear[3] = this.price(month4);
        priceOfYear[4] = this.price(month5);
        priceOfYear[5] = this.price(month6);
        priceOfYear[6] = this.price(month7);
        priceOfYear[7] = this.price(month8);
        priceOfYear[8] = this.price(month9);
        priceOfYear[9] = this.price(month10);
        priceOfYear[10] = this.price(month11);
        priceOfYear[11] = this.price(month12);


        return priceOfYear;
    }

    @Override
    @Transactional
    public boolean updateQuantityForOrderItem(orderItemModel orderItemModel) {
        Optional<orderItemModel> itemOrder = this.orderItemRepository.findById(orderItemModel.getId());
        if (itemOrder.isPresent()) {
            orderItemModel updateItem = itemOrder.get();
            if (orderItemModel.getQuantity() == 0) {
                this.orderItemRepository.deleteById(updateItem.getId());
            } else {
                updateItem.setQuantity(orderItemModel.getQuantity());
                this.orderItemRepository.save(updateItem);
            }
            return true;
        }
        return false;
    }

}
