package com.gm.wj.service;

import com.gm.wj.dao.AdminRoleDAO;
import com.gm.wj.entity.AdminMenu;
import com.gm.wj.entity.AdminPermission;
import com.gm.wj.entity.AdminRole;
import com.gm.wj.entity.AdminUserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Evan
 * @date 2019/11
 */
@Service
public class AdminRoleService {
    @Autowired
    AdminRoleDAO adminRoleDAO;
    @Autowired
    UserService userService;
    @Autowired
    AdminUserRoleService adminUserRoleService;
    @Autowired
    AdminPermissionService adminPermissionService;
    @Autowired
    AdminRolePermissionService adminRolePermissionService;
    @Autowired
    AdminMenuService adminMenuService;
    //查询全部角色
    public List<AdminRole> listWithPermsAndMenus() {
        List<AdminRole> roles = adminRoleDAO.findAll();
        List<AdminPermission> perms;
        List<AdminMenu> menus;
        for (AdminRole role : roles) {
            perms = adminPermissionService.listPermsByRoleId(role.getId());  //分配每个角色的资源
            menus = adminMenuService.getMenusByRoleId(role.getId());  //分配每个角色的菜单
            role.setPerms(perms);
            role.setMenus(menus);
        }
        return roles;
    }
    //查询全部角色
    public List<AdminRole> findAll() {
        return adminRoleDAO.findAll();
    }

    //没有id添加角色否则进行更新
    public void addOrUpdate(AdminRole adminRole) {
        adminRoleDAO.save(adminRole);
    }

    //根据角色名查看角色权限
    public List<AdminRole> listRolesByUser(String username) {
        int uid = userService.findByUsername(username).getId();
        List<Integer> rids = adminUserRoleService.listAllByUid(uid) //根据用户id得到角色权限的id的集合
                .stream().map(AdminUserRole::getRid).collect(Collectors.toList());

        return adminRoleDAO.findAllById(rids);
    }
    //根据角色名修改角色的状态
    public AdminRole updateRoleStatus(AdminRole role) {
        AdminRole roleInDB = adminRoleDAO.findById(role.getId());
        roleInDB.setEnabled(role.isEnabled());
        return adminRoleDAO.save(roleInDB);
    }
    //添加角色的时候根据id赋值资源权限
    public void editRole(@RequestBody AdminRole role) {
        adminRoleDAO.save(role);
        adminRolePermissionService.savePermChanges(role.getId(), role.getPerms());
    }

    //删除角色
    public void deleteRole(int id) {

        adminRoleDAO.deleteById(id);
    }
}
