package com.skt.item.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.skt.item.mapper.BrandMapper;
import com.skt.pojo.Brand;
import com.skt.item.service.BrandService;
import skt.common.pojo.PageResult;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
@Service
public class BrandServiceImpl implements BrandService {
    @Autowired
    private BrandMapper brandMapper;
    /**
     * 根据查询条件分页并排序查询品牌信息
     * @param key
     * @param page
     * @param rows
     * @param sortBy
     * @param desc
     * @return
     */
    @Override
    public PageResult<Brand> queryBrandsByPage(String key, Integer page, Integer rows, String sortBy, Boolean desc) {
        // 初始化example对象
        Example example = new Example(Brand.class);
        Example.Criteria criteria = example.createCriteria();
        // 根据name模糊查询，或者根据首字母查询
        if(StringUtils.isNotBlank(key)){
            criteria.andLike("name","%"+key+"%").orEqualTo("letter",key);
        }
        // 添加分页条件
        PageHelper.startPage(page,rows);
        // 添加排序条件
        if(StringUtils.isNotBlank(sortBy)){
            example.setOrderByClause(sortBy+" "+(desc?"desc":"asc"));
        }
        List<Brand> brands = this.brandMapper.selectByExample(example);
        System.out.println(brands);
        System.out.println(brands.get(0));
        //包装成pageinfo
        PageInfo<Brand> pageInfo = new PageInfo<>(brands);
        //包装成分页结果集返回
        return new PageResult<>(pageInfo.getTotal(),pageInfo.getList());
    }

    /**
     * 新增品牌
     * @param brand
     * @param cids
     */
    @Transactional
    public void saveBrand(Brand brand, List<Long> cids) {
        //新增brand
        this.brandMapper.insertSelective(brand);
        //再新增中间表
        cids.forEach(cid -> {
            this.brandMapper.insertCategoryAndBrand(cid,brand.getId());
        });
    }

    @Transactional
    public void editBrand(Brand brand, List<Long> cids) {
        this.brandMapper.updateByPrimaryKey(brand);
        this.brandMapper.deleteCategoryAndBrand(brand.getId());
        cids.forEach((cid) -> this.brandMapper.insertCategoryAndBrand(cid,brand.getId()));
    }

    @Override
    @Transactional
    public void deleteBrand(Long bid) {
        Brand brand = new Brand();
        brand.setId(bid);
        this.brandMapper.delete(brand);
        this.brandMapper.deleteCategoryAndBrand(bid);
    }

    @Override
    public List<Brand> queryBrandByCid(Long cid) {
        return this.brandMapper.queryBrandByCid(cid);
    }

    @Override
    public Brand queryBrandById(Long id) {
        Brand b= new Brand();
        b.setId(id);
        return this.brandMapper.selectByPrimaryKey(b);
    }
}
