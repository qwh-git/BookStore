package com.gm.wj.service;

import com.gm.wj.dao.BookDAO;
import com.gm.wj.entity.AdminBookShop;
import com.gm.wj.entity.Book;
import com.gm.wj.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Evan
 * @date 2019/4
 */
@Service
public class BookService {
    @Autowired
    BookDAO bookDAO;
    @Autowired
    CategoryService categoryService;
    //根据DESC降序显示图书
    public List<Book> list() {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        return bookDAO.findAll(sort);
    }

    public  Book findById(int id){

        return  bookDAO.findById(id);
    }

    //没有id添加图书否则进行更新
    public void addOrUpdate(Book book) {
        bookDAO.save(book);
    }
    //根据id删除图书
    public void deleteById(int id) {
        bookDAO.deleteById(id);
    }
    //根据类型id查询图书
    public List<Book> listByCategory(int cid) {
        Category category = categoryService.get(cid);
        return bookDAO.findAllByCategory(category);
    }
    //根据图书名称查询图书
    public List<Book> Search(String keywords) {
        return bookDAO.findAllByTitleLikeOrAuthorLike('%' + keywords + '%', '%' + keywords + '%');
    }


}
