package com.skt.item.service.Impl;

import com.skt.pojo.Category;
import com.skt.item.mapper.CategoryMapper;
import com.skt.item.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;
    @Override
    public List<Category> queryCategoryByPid(Long pid) {
        Category category = new Category();
        category.setParentId(pid);
        return this.categoryMapper.select(category);
    }
    /**
     * 根据品牌Id(bid)查找所属品牌分类
     * @param bid
     * @return
     */
    @Override
    public List<Category> queryByBrandId(Long bid) {
        return this.categoryMapper.queryByBrandId(bid);
    }

    public List<String> queryCateNames(List<Long> ids) {
        List<Category> categories = categoryMapper.selectByIdList(ids);
        List<String> names = new ArrayList<String>();
        for( Category categorie : categories){
            names.add(categorie.getName());
        }
        return names;
    }

    @Override
    public List<String> queryNamesByIds(List<Long> ids) {
        List<String> names = new ArrayList<String>();
        ids.forEach(id->{
           String name = this.categoryMapper.selectByPrimaryKey(id).getName();
           System.out.println("name=="+name);
           names.add(name);
        });
        return names;
    }
}
