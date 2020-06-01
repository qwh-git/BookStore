package com.gm.wj.service;

import com.alibaba.fastjson.JSONObject;
import com.gm.wj.dao.AdminBookShopDao;
import com.gm.wj.entity.AdminBookShop;
import com.gm.wj.entity.Book;
import com.gm.wj.entity.Item;
import com.gm.wj.entity.Order;
import org.elasticsearch.index.mapper.Uid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class AdminBookShopService {
    @Autowired
    AdminBookShopDao adminBookShopDao;
    @Autowired
    BookService bookService;
    @Autowired
    OrderService orderService;
    @Autowired
    UserService userService;
    @Autowired
    ItemService itemService;
    //没有id添加购物车否则进行更新
    public void addOrUpdate(AdminBookShop adminBookShop) {
        adminBookShopDao.save(adminBookShop);
    }


    public AdminBookShop findByBidAndUid(int bid,int uid) {
        return adminBookShopDao.findByBidAndUid(bid,uid);
    }
    //删除购物车的商品
    public int deleteByUidAndBid(int uid,int bid) {
        adminBookShopDao.deleteAdminBookShopsByUidAndBid(uid,bid);

        return  findByUidsize(uid);
    }


    //删除购物车的商品
    public boolean clearshop(String param) {
        JSONObject jsonobject = JSONObject.parseObject(param);
        String uid = jsonobject.getJSONObject("param").getString("uid");
        String bidlist = jsonobject.getJSONObject("param").getString("bidlist");
        bidlist = bidlist.substring(0, bidlist.length() - 1);
        bidlist = UserService.removeCharAt(bidlist, 0);
        String[] bidlist1 = bidlist.split(",");
        AdminBookShop adminBookShop1;
        Book book;
        Order order=new Order();
        int k=0;
        for (int i=0;i<bidlist1.length;i++){
            adminBookShop1=adminBookShopDao.findByBidAndUid(Integer.valueOf(bidlist1[i]), Integer.valueOf(uid));
            book=bookService.findById(adminBookShop1.getBid());
            if (adminBookShop1.getCount()<=book.getReserve()){
            }else{
                k=1;
            }
        }
        if (k>0){
            return false;
        }else{
            int count = 0; int price = 0;
            for (int i=0;i<bidlist1.length;i++) {
                adminBookShop1 = adminBookShopDao.findByBidAndUid(Integer.valueOf(bidlist1[i]), Integer.valueOf(uid));
                book = bookService.findById(adminBookShop1.getBid());
                count+=adminBookShop1.getCount();
                price+=adminBookShop1.getCount()*book.getPrice();
            }
            order.setCount(count);
            order.setPrice(price);
            order.setTime(new Date());
            order.setType(0);
            order.setStatus(0);
            order.setUsername(userService.getById( Integer.valueOf(uid)).getUsername());
            orderService.addOrUpdate(order);


            for (int i=0;i<bidlist1.length;i++){
                adminBookShop1=adminBookShopDao.findByBidAndUid(Integer.valueOf(bidlist1[i]), Integer.valueOf(uid));
                book=bookService.findById(adminBookShop1.getBid());
                book.setReserve(book.getReserve()-adminBookShop1.getCount());
                Item item = new Item();
                item.setOid(order.getId());
                item.setBid(adminBookShop1.getBid());
                item.setCount(adminBookShop1.getCount());
                item.setPrice(adminBookShop1.getCount()*book.getPrice());
                item.setDj(book.getPrice());
                item.setTitle(book.getTitle());
                itemService.addOrUpdate(item);
                bookService.addOrUpdate(book);
                adminBookShopDao.deleteAdminBookShopsByUidAndBid(adminBookShop1.getUid(),adminBookShop1.getBid());
            }

            return true;

        }
    }

    //根据用户查询购物车
    public List<AdminBookShop> findByUid(int uid) {
       List<AdminBookShop>list=adminBookShopDao.findByUid(uid);
       List<Book> books=new ArrayList<>();
       for (AdminBookShop l:list){
           books.add(bookService.findById(l.getBid()));
       }
        for (AdminBookShop l:list){
          l.setBooks1(books);
        }
        return adminBookShopDao.findByUid(uid);
    }


    //根据用户查询购物车
    public Integer findByUidsize(int uid) {
        int sum=0;
       List<AdminBookShop> list=adminBookShopDao.findByUid(uid);
       for (AdminBookShop adminBookShop:list){
           sum+=adminBookShop.getCount();
       }
       System.out.println("放回="+(sum==0?null:sum));
        return sum==0?null:sum;
    }

    //修改购物车的数量
    public void updateShopCount(AdminBookShop adminBookShop) {
        AdminBookShop adminBookShop1 = adminBookShopDao.findByBidAndUid(adminBookShop.getBid(),adminBookShop.getUid());
        if(adminBookShop.getId()==0){
            adminBookShop1.setCount(adminBookShop.getCount());
        }else{
            adminBookShop1.setCount(adminBookShop1.getCount()+1);
        }
        adminBookShopDao.save(adminBookShop1);
    }
}
