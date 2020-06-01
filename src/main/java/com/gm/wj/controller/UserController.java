package com.gm.wj.controller;

import com.gm.wj.entity.*;
import com.gm.wj.result.Result;
import com.gm.wj.result.ResultFactory;
import com.gm.wj.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * User controller.
 *
 * @author Evan
 * @date 2019/11
 */

@RestController
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    AdminUserRoleService adminUserRoleService;

    //查询全部用户
    @GetMapping("/api/admin/user")
    public Result listUsers() {
        return ResultFactory.buildSuccessResult(userService.list());
    }

    //修改用户状态
    @PutMapping("/api/admin/user/status")
    public Result updateUserStatus(@RequestBody @Valid User requestUser) {
        userService.updateUserStatus(requestUser);
        return ResultFactory.buildSuccessResult("用户状态更新成功");
    }

    //重置密码
    @PutMapping("/api/admin/user/password")
    public Result resetPassword(@RequestBody @Valid User requestUser) {
        userService.resetPassword(requestUser);
        return ResultFactory.buildSuccessResult("重置密码成功");
    }

    //修改用户和权限
    @PutMapping("/api/admin/user")
    public Result editUser(@RequestBody @Valid User requestUser) {
        userService.editUser(requestUser);
        return ResultFactory.buildSuccessResult("修改用户信息成功");
    }

    //删除用户
    @PostMapping("/api/admin/user/deletuser")
    public Result deleteUser(@RequestBody User requestUser) {
        userService.deleteById(requestUser.getId());
        return ResultFactory.buildSuccessResult("删除用户信息成功");
    }

    //删除用户
    @PostMapping("/api/admin/user/alldeletuser")
    public Result AlldeletUser(@RequestBody @Valid  String uerid) {
        userService.AlldeleteById(uerid);
        return ResultFactory.buildSuccessResult("删除用户信息成功");
    }
}
