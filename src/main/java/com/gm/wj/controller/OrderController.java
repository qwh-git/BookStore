package com.gm.wj.controller;

import com.gm.wj.result.Result;
import com.gm.wj.result.ResultFactory;
import com.gm.wj.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.List;

@RestController
public class OrderController {
    @Autowired
    OrderService orderService;
    @GetMapping("/api/OrderSize")
    public Result OrderSize(@RequestParam("username") String username) {

        return ResultFactory.buildSuccessResult(orderService.OrderSize(username));
    }

    @GetMapping("/api/OrderList")
    public Result OrderList(@RequestParam("username") String username) throws ParseException {

        return ResultFactory.buildSuccessResult(orderService.OrderList(username));
    }
}
