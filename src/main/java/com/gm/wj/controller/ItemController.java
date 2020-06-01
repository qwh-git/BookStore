package com.gm.wj.controller;

import com.gm.wj.result.Result;
import com.gm.wj.result.ResultFactory;
import com.gm.wj.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ItemController {
    @Autowired
    ItemService itemService;

    @GetMapping("/api/getByOid")
    public Result getByOid(@RequestParam("oid") int oid) {
        return ResultFactory.buildSuccessResult(itemService.getByOid(oid));
    }
}
