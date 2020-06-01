package com.gm.wj.dao;

import com.gm.wj.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemDao extends JpaRepository<Item,Integer> {

      List<Item> getByOid(int oid);

}
