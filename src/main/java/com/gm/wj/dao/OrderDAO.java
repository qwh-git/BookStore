package com.gm.wj.dao;

import com.gm.wj.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDAO extends JpaRepository<Order,Integer> {
    List<Order> getByUsername(String username);

}
