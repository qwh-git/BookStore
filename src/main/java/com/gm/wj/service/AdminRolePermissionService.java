package com.gm.wj.service;

import com.gm.wj.dao.AdminRolePermissionDAO;
import com.gm.wj.entity.AdminPermission;
import com.gm.wj.entity.AdminRolePermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Evan
 * @date 2019/11
 */
@Service
public class AdminRolePermissionService {
    @Autowired
    AdminRolePermissionDAO adminRolePermissionDAO;
    //根据角色id获取对应的资源
    List<AdminRolePermission> findAllByRid(int rid) {
        return adminRolePermissionDAO.findAllByRid(rid);
    }

    //删除该角色对应的资源关系&赋值资源给角色&保存角色对应的赋值关系
    @Transactional
    public void savePermChanges(int rid, List<AdminPermission> perms) {
        adminRolePermissionDAO.deleteAllByRid(rid);//删除该角色对应的资源关系
        List<AdminRolePermission> rps = new ArrayList<>();
        perms.forEach(p -> {
            AdminRolePermission rp = new AdminRolePermission(); //赋值资源给角色
            rp.setRid(rid);
            rp.setPid(p.getId());
            rps.add(rp);
        }); //保存角色对应的赋值关系
        adminRolePermissionDAO.saveAll(rps);
    }
}
