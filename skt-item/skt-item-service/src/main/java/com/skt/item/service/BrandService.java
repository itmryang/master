package com.skt.item.service;

import com.skt.pojo.Brand;
import skt.common.pojo.PageResult;

import java.util.List;

public interface BrandService {

    PageResult<Brand> queryBrandsByPage(String key, Integer page, Integer rows, String sortBy, Boolean desc);

    void saveBrand(Brand brand, List<Long> cids);

    void editBrand(Brand brand, List<Long> cids);

    void deleteBrand(Long bid);

    List<Brand> queryBrandByCid(Long cid);

    Brand queryBrandById(Long id);
}
