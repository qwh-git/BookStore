package com.gm.wj.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gm.wj.entity.AdminBookShop;
import com.gm.wj.entity.Book;
import com.gm.wj.result.Result;
import com.gm.wj.result.ResultFactory;
import com.gm.wj.service.AdminBookShopService;
import com.gm.wj.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class ShopController {
    @Autowired
    AdminBookShopService adminBookShopService;

    @PostMapping("/api/addOrUpdateShop")
    public Result addOrUpdateBooks(@RequestBody @Valid AdminBookShop adminBookShop) {
        if (adminBookShopService.findByBidAndUid(adminBookShop.getBid(),adminBookShop.getUid())!=null){
            adminBookShopService.updateShopCount(adminBookShop);
        }else{
            adminBookShopService.addOrUpdate(adminBookShop);
        }
        return ResultFactory.buildSuccessResult("修改成功");
    }

    @GetMapping("/api/findByList")
    public Result findByList(@RequestParam("uid") int uid) {
        return ResultFactory.buildSuccessResult(adminBookShopService.findByUid(uid));
    }

    //删除图书
    @PostMapping("/api/shopdelete")
    public Result deleteShop(@RequestBody @Valid AdminBookShop adminBookShop) {
        return ResultFactory.buildSuccessResult(adminBookShopService.deleteByUidAndBid(adminBookShop.getUid(),adminBookShop.getBid()));
    }

    //购物车付款
    @PostMapping("/api/clearshop")
    public Result clearshop(@RequestBody String param) {

        return ResultFactory.buildSuccessResult(adminBookShopService.clearshop(param));
    }


    @GetMapping("/api/findByUid")
    public Result findByUid(@RequestParam("uid") int uid) {
        return ResultFactory.buildSuccessResult(adminBookShopService.findByUidsize(uid));
    }

    //根据第几页，显示多少条，DESC降序显示购物车
    @GetMapping("/api/shop")
    public Result listArticles() {
        return ResultFactory.buildSuccessResult(adminBookShopService.list());
    }

}
