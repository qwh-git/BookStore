package com.gm.wj.service;

import com.gm.wj.dao.JotterArticleDAO;
import com.gm.wj.entity.JotterArticle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * @author Evan
 * @date 2020/1/14 21:00
 */
@Service
public class JotterArticleService {


    @Autowired
    JotterArticleDAO jotterArticleDAO;

    //根据第几页，显示多少条，DESC降序显示文章
    public Page list(int page, int size) {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        return jotterArticleDAO.findAll(PageRequest.of(page, size, sort));
    }
    //根据文章id查询文章
    public JotterArticle findById(int id) {
        return jotterArticleDAO.findById(id);
    }
    //没有id添加文章否则进行更新
    public void addOrUpdate(JotterArticle article) {
        jotterArticleDAO.save(article);
    }
    //根据id删除文章
    public void delete(int id) {
        jotterArticleDAO.deleteById(id);
    }

}
