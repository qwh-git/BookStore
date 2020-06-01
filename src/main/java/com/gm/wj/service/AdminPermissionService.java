package com.gm.wj.service;

import com.gm.wj.dao.AdminPermissionDAO;
import com.gm.wj.dao.AdminRolePermissionDAO;
import com.gm.wj.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Evan
 * @date 2019/11
 */
@Service
public class AdminPermissionService {
    @Autowired
    AdminPermissionDAO adminPermissionDAO;
    @Autowired
    AdminUserRoleService adminUserRoleService;
    @Autowired
    AdminRoleService adminRoleService;
    @Autowired
    AdminRolePermissionService adminRolePermissionService;
    @Autowired
    AdminRolePermissionDAO adminRolePermissionDAO;
    @Autowired
    UserService userService;
    //查询全部的资源
    public List<AdminPermission> list() {return adminPermissionDAO.findAll();}

    //判断url是否和数据库的url前缀一样
    public boolean needFilter(String requestAPI) {
        List<AdminPermission> ps = adminPermissionDAO.findAll();
        for (AdminPermission p: ps) {
            // 判断前缀相等
            if (requestAPI.startsWith(p.getUrl())) {
                return true;
            }
        }
        return false;
    }

    //根据角色id集合的到对应的角色资源
    public List<AdminPermission> listPermsByRoleId(int rid) {
        List<Integer> pids = adminRolePermissionService.findAllByRid(rid)
                .stream().map(AdminRolePermission::getPid).collect(Collectors.toList());
        return adminPermissionDAO.findAllById(pids);
    }
    //根据用户名得到角色相对应的角色资源的url
    public Set<String> listPermissionURLsByUser(String username) {
        //根据用户得到对应的角色id集合
        List<Integer> rids = adminRoleService.listRolesByUser(username)
                .stream().map(AdminRole::getId).collect(Collectors.toList());
        //根据角色id集合得到对应的资源id集合
        List<Integer> pids = adminRolePermissionDAO.findAllByRid(rids)
                .stream().map(AdminRolePermission::getPid).collect(Collectors.toList());
        //根据资源id集合查询对应的资源
        List<AdminPermission> perms = adminPermissionDAO.findAllById(pids);
        //获取对应资源id的url
        Set<String> URLs = perms.stream().map(AdminPermission::getUrl).collect(Collectors.toSet());

        return URLs;
    }
}
