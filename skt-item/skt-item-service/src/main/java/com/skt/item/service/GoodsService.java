package com.skt.item.service;

import com.skt.pojo.Sku;
import com.skt.pojo.SpuDetail;
import com.skt.bo.SpuBo;
import skt.common.pojo.PageResult;

import java.util.List;

public interface GoodsService {
    PageResult<SpuBo> querySpuBoByPage(String key, Boolean saleable, Integer page, Integer row);

    void insertgoods(SpuBo spuBo);

    SpuDetail querySuDetailBySpuId(Long spuId);

    List<Sku> querySkuBySpuId(Long spuId);

    void updateGoods(SpuBo spuBo);

    void delGoodsBySpuId(Long spuId);

    void updateSaleable(Long spuId);
}
