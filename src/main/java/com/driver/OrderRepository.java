package com.driver;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Repository
public class OrderRepository {

    HashMap<String,Order> orderDB= new HashMap<>();
    HashMap<String,DeliveryPartner> partnerDB= new HashMap<>();
    HashMap<String,String> orderPartnerDB= new HashMap<>();
    public OrderRepository(){

    }
    public void addOrder(Order order){
        orderDB.put(order.getId(),order);
    }
    public void addPartner(DeliveryPartner partner){
        partnerDB.put(partner.getId(),partner);
    }
    public Order getOrderById(String orderId){
        return orderDB.get(orderId);
    }

    public DeliveryPartner getPartnerById(String partnerId) {
        return partnerDB.get(partnerId);
    }

    public List<String> getAllOrders() {
        List<String> list = new ArrayList<>();
        for (String s : orderDB.keySet()) {
            list.add(s);
        }
        return list;
    }

    public void addOrderPartnerPair(String orderId, String partnerId) {
        DeliveryPartner partner= getPartnerById(partnerId);
        int numberofOrders=partner.getNumberOfOrders();
        partner.setNumberOfOrders(numberofOrders+1);
        orderPartnerDB.put(orderId,partnerId);
    }

    public Map<String, String> getOrderPartnerDB() {
        return orderPartnerDB;
    }

    public Integer getCountOfUnassignedOrders() {
        return orderDB.size()-orderPartnerDB.size();
    }

    public List<String> getOrdersByPartnerId(String partnerId) {

        List<String> orders= new ArrayList<>();
        for(String orderId:orderPartnerDB.keySet()){
            if(orderPartnerDB.get(orderId).equals(partnerId)){
                orders.add(orderId);
            }
        }
        return orders;
    }

    public void deletePartnerById(String partnerId) {
        if(partnerDB.containsKey(partnerId)){
            partnerDB.remove(partnerId);
            for(String orderId:orderPartnerDB.keySet()){
                if(orderPartnerDB.get(orderId).equals(partnerId)){
                   orderPartnerDB.remove(orderId);
                }
            }
        }
      }

    public void deleteOrderById(String orderId) {
        if(orderDB.containsKey(orderId)){
            orderDB.remove(orderId);
            for(String orderid:orderPartnerDB.keySet()){
                if(orderid.equals(orderId)){
                    DeliveryPartner partner=getPartnerById(orderPartnerDB.get(orderid));
                    int countOrders=partner.getNumberOfOrders();
                    partner.setNumberOfOrders(countOrders-1);
                    orderPartnerDB.remove(orderid);
                }
            }
        }
    }

}
