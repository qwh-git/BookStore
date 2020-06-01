package com.gm.wj.service;

import com.gm.wj.dao.AdminRoleMenuDAO;
import com.gm.wj.entity.AdminRole;
import com.gm.wj.entity.AdminRoleMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Evan
 * @date 2019/11
 */
@Service
public class AdminRoleMenuService {
    @Autowired
    AdminRoleMenuDAO adminRoleMenuDAO;
    //根据角色id获取菜单集合的关系
    public List<AdminRoleMenu> findAllByRid(int rid) {
        return adminRoleMenuDAO.findAllByRid(rid);
    }
    //根据角色id集合获取菜单集合的关系
    public List<AdminRoleMenu> findAllByRid(List<Integer> rids) {
        return adminRoleMenuDAO.findAllByRid(rids);
    }
    //保存角色对应的菜单关系
    public void save(AdminRoleMenu rm) {
        adminRoleMenuDAO.save(rm);
    }


    //删除该角色对应的菜单关系&赋值菜单给角色$保存角色对应的菜单关系
    @Modifying
    @Transactional
    public void updateRoleMenu(int rid, Map<String, List<Integer>> menusIds) {
        adminRoleMenuDAO.deleteAllByRid(rid);//删除该角色对应的菜单关系
        List<AdminRoleMenu> rms = new ArrayList<>();
        for (Integer mid : menusIds.get("menusIds")) { //赋值菜单给角色
            AdminRoleMenu rm = new AdminRoleMenu();
            rm.setMid(mid);
            rm.setRid(rid);
            rms.add(rm);
        }
        //保存角色对应的菜单关系
        adminRoleMenuDAO.saveAll(rms);
    }
}
