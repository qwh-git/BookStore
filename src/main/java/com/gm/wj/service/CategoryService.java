package com.gm.wj.service;

import com.gm.wj.dao.CategoryDAO;
import com.gm.wj.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Evan
 * @date 2019/4
 */
@Service
public class CategoryService {
    @Autowired
    CategoryDAO categoryDAO;
    //根据DESC降序显示图书类型
    public List<Category> list() {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        return categoryDAO.findAll(sort);
    }
    //根据图书类型id获取图书类型
    public Category get(int id) {
        Category c= categoryDAO.findById(id).orElse(null);
        return c;
    }
}
