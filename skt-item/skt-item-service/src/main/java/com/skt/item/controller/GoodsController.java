package com.skt.item.controller;

import com.skt.pojo.Sku;
import com.skt.pojo.SpuDetail;
import com.skt.bo.SpuBo;
import com.skt.item.service.Impl.GoodsServiceImpl;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import skt.common.pojo.PageResult;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class GoodsController {
    @Autowired
    private GoodsServiceImpl goodsService;
    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @RequestMapping("spu/page")
    public ResponseEntity<PageResult<SpuBo>> querySpuByPage(
            @RequestParam(value = "key", required = false) String key,
            @RequestParam(value = "saleable", required = false) Boolean saleable,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "5") Integer rows
    ) {
        PageResult<SpuBo> result = this.goodsService.querySpuBoByPage(key, saleable, page, rows);
        if (CollectionUtils.isEmpty(result.getItems())) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }

    /**
     * 添加goods
     *
     * @param spuBo
     * @return
     */
    @RequestMapping("goods")
    public ResponseEntity<Void> insertgoods(@RequestBody SpuBo spuBo) {
        rocketMQTemplate.syncSend("jian","jian");
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 修改商品信息
     * @param spuBo
     * @return
     */
    @PutMapping("goods")
    public ResponseEntity<Void> updateGoods(@RequestBody SpuBo spuBo){
        this.goodsService.updateGoods(spuBo);
        return  ResponseEntity.noContent().build();
    }
    /**
     * 编辑时，数据回显
     * @param spuId
     * @return
     */
    @GetMapping("spu/detail/{spuId}")
    public ResponseEntity<SpuDetail> querySpuDetailBySpuId(@PathVariable Long spuId) {
        SpuDetail spuDetail = this.goodsService.querySuDetailBySpuId(spuId);
        if (spuDetail == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(spuDetail);
    }

    /**
     * 编辑时，数据回显
     *
     * @param spuId
     * @return
     */
    @GetMapping("sku/list")
    public ResponseEntity<List<Sku>> querySkuBySpuId(@RequestParam("id") Long spuId) {
        List<Sku> skus = this.goodsService.querySkuBySpuId(spuId);
        if (CollectionUtils.isEmpty(skus)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(skus);
    }

    /**
     * 删除商品，即修改spu
     * @param spuId
     * @return
     */
    @RequestMapping("spu/delGoods/{spuId}")
    public ResponseEntity<Void> delGoodsBySpuId(@PathVariable Long spuId){
        this.goodsService.delGoodsBySpuId(spuId);
        return ResponseEntity.noContent().build();
    }

    /**
     * 修改商品上下架
     * @param spuId
     * @return
     */
    @RequestMapping("spu/updateSaleable/{spuId}")
    public ResponseEntity<Void> updateSaleable(@PathVariable Long spuId){
        this.goodsService.updateSaleable(spuId);
        return ResponseEntity.noContent().build();
    }
}
