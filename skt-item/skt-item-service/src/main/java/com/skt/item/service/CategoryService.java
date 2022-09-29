package com.skt.item.service;

import com.skt.pojo.Category;

import java.util.List;

public interface CategoryService {
    List<Category> queryCategoryByPid(Long pid);

    List<Category> queryByBrandId(Long bid);

    List<String> queryCateNames(List<Long> ids);

    List<String> queryNamesByIds(List<Long> ids);
}
