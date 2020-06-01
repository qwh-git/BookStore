package com.gm.wj.controller;

import com.gm.wj.result.Result;
import com.gm.wj.result.ResultFactory;
import com.gm.wj.service.AdminMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Menu controller.
 *
 * @author Evan
 * @date 2019/11
 */
@RestController
public class MenuController {
    @Autowired
    AdminMenuService adminMenuService;

    //根据用户名获取角色该有的菜单权限
    @GetMapping("/api/menu")
    public Result menu() {
        return ResultFactory.buildSuccessResult(adminMenuService.getMenusByCurrentUser());
    }

    //根据角色id查询菜单的权限
    @GetMapping("/api/admin/role/menu")
    public Result listAllMenus() {
        return ResultFactory.buildSuccessResult(adminMenuService.getMenusByRoleId(1));
    }
}
