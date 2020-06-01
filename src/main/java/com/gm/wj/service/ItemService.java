package com.gm.wj.service;

import com.gm.wj.dao.ItemDao;
import com.gm.wj.entity.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {
    @Autowired
    ItemDao itemDao;

    public void addOrUpdate(Item item) {
        itemDao.save(item);
    }

    public List<Item> getByOid(int oid){

      return   itemDao.getByOid(oid);
    }
}
