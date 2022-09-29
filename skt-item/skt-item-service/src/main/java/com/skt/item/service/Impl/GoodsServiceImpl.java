package com.skt.item.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.skt.item.mapper.*;
import com.skt.pojo.Sku;
import com.skt.pojo.Spu;
import com.skt.pojo.SpuDetail;
import com.skt.pojo.Stock;
import com.skt.bo.SpuBo;
import com.skt.item.service.GoodsService;
import skt.common.pojo.PageResult;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class GoodsServiceImpl implements GoodsService {
    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private CategoryServiceImpl categoryService;
    @Autowired
    private BrandMapper brandMapper;
    @Autowired
    private SkuMapper skuMapper;
    @Autowired
    private SupDetailMapper supDetailMapper;
    @Autowired
    private StockeMapper stockeMapper;

    @Override
    public PageResult<SpuBo> querySpuBoByPage(String key, Boolean saleable, Integer page, Integer row) {
        Example example = new Example(Spu.class);
        Example.Criteria criteria = example.createCriteria();
        //搜索条件
        if (StringUtils.isNotBlank(key)) {
            criteria.andLike("title", "%" + key + "%");
        }
        if (saleable != null) {
            criteria.andEqualTo("saleable", saleable);
        }
        criteria.andEqualTo("valid",1);
        //分页条件
        PageHelper.startPage(page, row);
        //开始查询
        List<Spu> spus = goodsMapper.selectByExample(example);
        PageInfo pageInfo = new PageInfo<>(spus);
        List<SpuBo> spuBos = new ArrayList<>();
        spus.forEach(spu -> {
            SpuBo spuBo = new SpuBo();
            //复制spu属性到spuBo
            BeanUtils.copyProperties(spu, spuBo);
            //查询分类名称
            List<String> cnames = this.categoryService.queryCateNames(Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3()));
            spuBo.setCname(StringUtils.join(cnames, "/"));
            //查询品牌名称
            spuBo.setBname(this.brandMapper.selectByPrimaryKey(spu.getBrandId()).getName());
            spuBos.add(spuBo);
        });

        return new PageResult<SpuBo>(pageInfo.getTotal(), spuBos);
    }

    @Transactional
    @Override
    public void insertgoods(SpuBo spuBo) {
        //添加spu
        spuBo.setId(null);
        spuBo.setSaleable(true);
        spuBo.setValid(true);
        spuBo.setCreateTime(new Date());
        spuBo.setLastUpdateTime(spuBo.getCreateTime());
        this.goodsMapper.insertSelective(spuBo);
        //添加spuDetail
        SpuDetail spuDetail = spuBo.getSpuDetail();
        spuDetail.setSpuId(spuBo.getId());
        this.supDetailMapper.insertSelective(spuDetail);
        saveSkuAndStock(spuBo);

    }

    private void saveSkuAndStock(SpuBo spuBo) {
        //添加sku
        List<Sku> skus = spuBo.getSkus();
        skus.forEach(sku -> {
            sku.setSpuId(spuBo.getId());
            sku.setCreateTime(new Date());
            sku.setLastUpdateTime(sku.getCreateTime());
            this.skuMapper.insertSelective(sku);
            //添加stoke库存
            Stock stock = new Stock();
            stock.setSkuId(sku.getId());
            stock.setStock(sku.getStock());
            this.stockeMapper.insertSelective(stock);
        });
    }

    @Override
    public SpuDetail querySuDetailBySpuId(Long spuId) {
        return supDetailMapper.selectByPrimaryKey(spuId);
    }

    @Override
    public List<Sku> querySkuBySpuId(Long spuId) {
        Sku sku = new Sku();
        sku.setSpuId(spuId);
        List<Sku> skus = skuMapper.select(sku);
        skus.forEach(sku1 -> {
            Stock stock = stockeMapper.selectByPrimaryKey(sku1.getId());
            sku1.setStock(stock.getStock());
        });
        return skus;
    }

    @Transactional
    @Override
    public void updateGoods(SpuBo spuBo) {

        List<Sku> skus = this.querySkuBySpuId(spuBo.getId());
        if (CollectionUtils.isNotEmpty(skus)) {
            skus.forEach(sku -> {
                //删除stock
                this.stockeMapper.deleteByPrimaryKey(sku.getId());
            });
        }
        //删除sku
        Sku sku = new Sku();
        sku.setSpuId(spuBo.getId());
        skuMapper.delete(sku);
        //新增sku
        //新增stock
        this.saveSkuAndStock(spuBo);
        //更新spuDetail
        supDetailMapper.updateByPrimaryKeySelective(spuBo.getSpuDetail());
        //更新spu
        spuBo.setSaleable(null);
        spuBo.setCreateTime(null);
        spuBo.setValid(null);
        spuBo.setLastUpdateTime(new Date());
        goodsMapper.updateByPrimaryKeySelective(spuBo);
    }

    @Override
    public void delGoodsBySpuId(Long spuId) {
        Spu spu = new Spu();
        spu.setId(spuId);
        spu.setValid(false);
        this.goodsMapper.updateByPrimaryKeySelective(spu);
    }

    @Override
    public void updateSaleable(Long spuId) {
        this.goodsMapper.updateSaleable(spuId);
    }

}
