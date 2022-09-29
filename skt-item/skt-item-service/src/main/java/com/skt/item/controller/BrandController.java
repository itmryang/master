package com.skt.item.controller;

import com.skt.item.utils.RedisLockUtils;
import com.skt.pojo.Brand;
import com.skt.item.service.BrandService;
import skt.common.pojo.PageResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("brand")
public class BrandController {
    @Autowired
    private BrandService brandService;
    @Autowired
    private RedisLockUtils redisLockUtils;

    private static final Logger logger = LoggerFactory.getLogger(BrandController.class);
    /**
     * 根据查询条件分页并排序查询品牌信息
     * @param key
     * @param page
     * @param rows
     * @param sortBy
     * @param desc
     * @return
     */
    @GetMapping("/page")
    public ResponseEntity<PageResult<Brand>> queryBrandsByPage(
            @RequestParam(value = "key", required = false)String key,
            @RequestParam(value = "page", defaultValue = "1")Integer page,
            @RequestParam(value = "rows", defaultValue = "5")Integer rows,
            @RequestParam(value = "sortBy", required = false)String sortBy,
            @RequestParam(value = "desc", required = false)Boolean desc
    ){
        PageResult<Brand> result = this.brandService.queryBrandsByPage(key,page,rows,sortBy,desc);
        if(CollectionUtils.isEmpty(result.getItems())){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }

    /**
     * 新增品牌
     * @param brand
     * @param cids
     * @return
     */
    @PostMapping
    public ResponseEntity<Void> saveBrand(Brand brand, @RequestParam("cids")List<Long> cids){
        this.brandService.saveBrand(brand,cids);
        return  ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @PutMapping
    public ResponseEntity<Void> editBrand(Brand brand, @RequestParam("cids")List<Long> cids){
        System.out.println("put修改");
        this.brandService.editBrand(brand,cids);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 删除品牌
     * @param bid
     * @return
     */
    @RequestMapping("/bid/{bid}")
    public ResponseEntity<Void> deleteBrand(@PathVariable Long bid){
        this.brandService.deleteBrand(bid);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 根据cid，查询brand_category表，查询到bid，再根据bid查询brand
     * @param cid
     * @return
     */
    @GetMapping("cid")
    public ResponseEntity<List<Brand>> queryBrandByCid(String  cid){
        Boolean iii = redisLockUtils.lock(cid);


//        List<Brand> brandList = this.brandService.queryBrandByCid(cid);
//        if(CollectionUtils.isEmpty(brandList)){
//            return ResponseEntity.notFound().build();
//        }
        return ResponseEntity.ok(null);
    }
    @GetMapping("id")
    public String queryBrandById(String id){
        redisLockUtils.unlock(id);
      return "ok";
    }
}

