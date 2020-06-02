package com.gm.wj.service;

import com.alibaba.fastjson.JSONObject;
import com.gm.wj.dao.UserDAO;
import com.gm.wj.dto.UserDTO;
import com.gm.wj.entity.AdminRole;
import com.gm.wj.entity.User;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Evan
 * @date 2019/4
 */
@Service
public class UserService {
    @Autowired
    UserDAO userDAO;
    @Autowired
    AdminRoleService adminRoleService;
    @Autowired
    AdminUserRoleService adminUserRoleService;

    //查询根据id查询用户
    public User getById(int id){

        return  userDAO.getById(id);
    }
    //查询全部用户
    public List<UserDTO> list() {
        List<User> users = userDAO.findAll();

        // Find all roles in DB to enable JPA persistence context.
//        List<AdminRole> allRoles = adminRoleService.findAll();

        List<UserDTO> userDTOS = users
                .stream().map(user -> (UserDTO) new UserDTO().convertFrom(user)).collect(Collectors.toList());

        userDTOS.forEach(u -> { //把查询出来的用户赋值给角色权限
            List<AdminRole> roles = adminRoleService.listRolesByUser(u.getUsername());
            u.setRoles(roles);
        });

        return userDTOS;
    }
    //没有id添加用户否则进行更新
    public void addOrUpdate(User user) {
        userDAO.save(user);
    }
    //判断用户名为空
    public boolean isExist(String username) {
        User user = userDAO.findByUsername(username);
        return null != user;
    }
    //根据用户名查询户名
    public User findByUsername(String username) {
        return userDAO.findByUsername(username);
    }
    //根据用户名和密码查询户名
    public User get(String username, String password) {
        return userDAO.getByUsernameAndPassword(username, password);
    }
    //注册放回int的状态
    public int register(User user) {
        String username = user.getUsername();
        String name = user.getName();
        String phone = user.getPhone();
        String email = user.getEmail();
        String password = user.getPassword();
        //过滤参数的html实现转义效果
        username = HtmlUtils.htmlEscape(username);
        user.setUsername(username);
        name = HtmlUtils.htmlEscape(name);
        user.setName(name);
        phone = HtmlUtils.htmlEscape(phone);
        user.setPhone(phone);
        email = HtmlUtils.htmlEscape(email);
        user.setEmail(email);
        user.setEnabled(true);

        if (username.equals("") || password.equals("")) {
            return 0;
        }

        boolean exist = isExist(username);

        if (exist) {
            return 2;
        }

        // 默认生成 16 位盐
        String salt = new SecureRandomNumberGenerator().nextBytes().toString();
        int times = 2;
        String encodedPassword = new SimpleHash("md5", password, salt, times).toString();

        user.setSalt(salt);
        user.setPassword(encodedPassword);

        userDAO.save(user);

        return 1;
    }
    //根据用户名修改用户的状态
    public void updateUserStatus(User user) {
        User userInDB = userDAO.findByUsername(user.getUsername());
        userInDB.setEnabled(user.isEnabled());
        userDAO.save(userInDB);
    }
    //重置密码为123
    public void resetPassword(User user) {
        User userInDB = userDAO.findByUsername(user.getUsername());
        String salt = new SecureRandomNumberGenerator().nextBytes().toString();
        int times = 2;
        userInDB.setSalt(salt);
        String encodedPassword = new SimpleHash("md5", "123", salt, times).toString();
        userInDB.setPassword(encodedPassword);
        userDAO.save(userInDB);
    }
    //修改用户和权限
    public void editUser(User user) {
        User userInDB = userDAO.findByUsername(user.getUsername());
        userInDB.setName(user.getName());
        userInDB.setPhone(user.getPhone());
        userInDB.setEmail(user.getEmail());
        userDAO.save(userInDB);
        adminUserRoleService.saveRoleChanges(userInDB.getId(), user.getRoles());
    }
    //根据id删除用户
    public void deleteById(int id) {
        userDAO.deleteById(id);
    }

    //批量删除用户
    public void AlldeleteById(String uerid) {
        JSONObject jsonobject = JSONObject.parseObject(uerid);
        String str = jsonobject.getJSONObject("params").getString("delarr");
        str = str.substring(0, str.length() - 1);
        str = removeCharAt(str, 0);
        String[] strs = str.split(",");
        for (String S : strs) {
            userDAO.deleteById(Integer.valueOf(S));
        }
    }

    public static String removeCharAt(String s, int pos) {
        return s.substring(0, pos) + s.substring(pos + 1);
    }
}
