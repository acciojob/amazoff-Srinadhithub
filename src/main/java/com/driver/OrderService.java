package com.driver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
@Service
public class OrderService {
    @Autowired
    OrderRepository orderRepository;
    public OrderService(){

    }

    public Order getOrderById(String orderId) {
        return orderRepository.getOrderById(orderId);
    }


    public Integer getOrderCountByPartnerId(String partnerId) {
        DeliveryPartner deliveryPartner= orderRepository.getPartnerById(partnerId);
        if(deliveryPartner==null) return 0;
        return deliveryPartner.getNumberOfOrders();
    }

    public void addOrder(Order order) {
        orderRepository.addOrder(order);
    }

    public void addPartner(DeliveryPartner partner) {
        orderRepository.addPartner(partner);
    }

    public DeliveryPartner getPartnerById(String partnerId) {
        return orderRepository.getPartnerById(partnerId);
    }

    public List<String> getAllOrders() {
        return orderRepository.getAllOrders();
    }

    public void addOrderPartnerPair(String orderId, String partnerId) {
        orderRepository.addOrderPartnerPair(orderId,partnerId);
    }

    public List<String> getOrdersByPartnerId(String partnerId) {
      return orderRepository.getOrdersByPartnerId(partnerId);
    }

    public Integer getCountOfUnassignedOrders() {
        return orderRepository.getCountOfUnassignedOrders();
    }

    public Integer getOrdersLeftAfterGivenTimeByPartnerId(String time, String partnerId) {
        int count=0;
        int giventime=Time.convertStringToInt(time);
        List<String> orders=orderRepository.getOrdersByPartnerId(partnerId);
       for(String orderId:orders) {
           Order order = orderRepository.getOrderById(orderId);
           if (order.getDeliveryTime() > giventime) {
               count++;
           }
       }
        return count;
    }

    public String getLastDeliveryTimeByPartnerId(String partnerId) {
        int time=0;
        List<String> orders=orderRepository.getOrdersByPartnerId(partnerId);
        for(String orderId:orders) {
            Order order = orderRepository.getOrderById(orderId);
           int curTime= order.getDeliveryTime();
            if (curTime > time) {
                time=curTime;
            }
        }
        return Time.convertIntToString(time);
    }

    public void deletePartnerById(String partnerId) {
        orderRepository.deletePartnerById(partnerId);
    }

    public void deleteOrderById(String orderId) {
        orderRepository.deleteOrderById(orderId);
    }
}
