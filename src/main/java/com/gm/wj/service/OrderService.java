package com.gm.wj.service;

import com.gm.wj.dao.OrderDAO;
import com.gm.wj.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    OrderDAO orderDAO;

    public void addOrUpdate(Order order) {
        orderDAO.save(order);
    }

    public Integer OrderSize(String username) {

        return orderDAO.getByUsername(username).size()==0?null:orderDAO.getByUsername(username).size();
    }

    public List<Order> OrderList(String username) {

        return orderDAO.getByUsername(username);
    }

    public List<Order> OrderList1() {

        return orderDAO.findAll();
    }
}
