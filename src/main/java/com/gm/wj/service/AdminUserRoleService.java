package com.gm.wj.service;

import com.gm.wj.dao.AdminUserRoleDAO;
import com.gm.wj.entity.AdminRole;
import com.gm.wj.entity.AdminUserRole;
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
public class AdminUserRoleService {
    @Autowired
    AdminUserRoleDAO adminUserRoleDAO;
    //根据用户id获取对应的角色关系
    public List<AdminUserRole> listAllByUid(int uid) {
        return adminUserRoleDAO.findAllByUid(uid);
    }

    //删除该用户对应的角色关系&赋值角色给用户$保存用户对应的角色关系
    @Transactional
    public void saveRoleChanges(int uid, List<AdminRole> roles) {
        adminUserRoleDAO.deleteAllByUid(uid);//删除该用户对应的角色关系
        List<AdminUserRole> urs = new ArrayList<>();
        roles.forEach(r -> {  //赋值角色给用户
            AdminUserRole ur = new AdminUserRole();
            ur.setUid(uid);
            ur.setRid(r.getId());
            urs.add(ur);
        });//保存用户对应的角色关系
        adminUserRoleDAO.saveAll(urs);
    }
}
