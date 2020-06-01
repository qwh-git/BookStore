package com.gm.wj.dao;


import com.gm.wj.entity.AdminBookShop;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface AdminBookShopDao extends JpaRepository<AdminBookShop, Integer> {
    AdminBookShop findByBidAndUid(int bid,int uid);

    List<AdminBookShop> findByUid(int uid);

    @Transactional
    int deleteAdminBookShopsByUidAndBid(int uid,int bid);

}
