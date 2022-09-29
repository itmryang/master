package com.skt.Api;

import com.skt.pojo.Sku;
import com.skt.pojo.SpuDetail;
import com.skt.bo.SpuBo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import skt.common.pojo.PageResult;

import java.util.List;
@FeignClient(value = "item-service")
public interface GoodsApi {

    /**
     * 分页查询商品
     *
     * @param page
     * @param rows
     * @param saleable
     * @param key
     * @return
     */
    @GetMapping("/spu/page")
    PageResult<SpuBo> querySpuByPage(
            @RequestParam(value = "key", required = false) String key,
            @RequestParam(value = "saleable", defaultValue = "true") Boolean saleable,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "5") Integer rows
    );

    /**
     * 根据spu商品id查询详情
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/category/spu/detail")
    SpuDetail querySpuDetailBySpuId(@RequestParam("id") Long id);

    /**
     * 根据spu的id查询sku
     *
     * @param id
     * @return
     */
    @GetMapping("sku/list")
    List<Sku> querySkuBySpuId(@RequestParam("id") Long id);
}