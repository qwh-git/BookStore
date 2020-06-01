package com.gm.wj.service;

import com.gm.wj.dao.AdminMenuDAO;
import com.gm.wj.entity.AdminMenu;
import com.gm.wj.entity.AdminRoleMenu;
import com.gm.wj.entity.AdminUserRole;
import com.gm.wj.entity.User;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Evan
 * @date 2019/10
 */
@Service
public class AdminMenuService {
    @Autowired
    AdminMenuDAO adminMenuDAO;
    @Autowired
    UserService userService;
    @Autowired
    AdminUserRoleService adminUserRoleService;
    @Autowired
    AdminRoleMenuService adminRoleMenuService;
    //根据菜单pid查询菜单
    public List<AdminMenu> getAllByParentId(int parentId) {
        return adminMenuDAO.findAllByParentId(parentId);
    }
    //根据用户名获取角色该有的菜单权限
    public List<AdminMenu> getMenusByCurrentUser() {
        // 获取授权的用户名
        String username = SecurityUtils.getSubject().getPrincipal().toString();
        User user = userService.findByUsername(username);

        //根据用户id获取角色id集合
        List<Integer> rids = adminUserRoleService.listAllByUid(user.getId())
                .stream().map(AdminUserRole::getRid).collect(Collectors.toList());

        //根据角色id获取菜单id集合
        List<Integer> menuIds = adminRoleMenuService.findAllByRid(rids)
                .stream().map(AdminRoleMenu::getMid).collect(Collectors.toList());

        //根据菜单id的集合查找该角色显示的菜单权限
        List<AdminMenu> menus = adminMenuDAO.findAllById(menuIds).stream().distinct().collect(Collectors.toList());

        handleMenus(menus);
        return menus;
    }
    //根据角色id查询菜单的权限
    public List<AdminMenu> getMenusByRoleId(int rid) {

        List<Integer> menuIds = adminRoleMenuService.findAllByRid(rid)
                .stream().map(AdminRoleMenu::getMid).collect(Collectors.toList());

        List<AdminMenu> menus = adminMenuDAO.findAllById(menuIds);

        handleMenus(menus);
        return menus;
    }

    //根据父菜单的id显示子菜单
    public void handleMenus(List<AdminMenu> menus) {
        menus.forEach(m -> {
            List<AdminMenu> children = getAllByParentId(m.getId());
            m.setChildren(children);
        });

        menus.removeIf(m -> m.getParentId() != 0);
    }
}
