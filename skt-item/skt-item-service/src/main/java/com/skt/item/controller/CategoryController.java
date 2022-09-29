package com.skt.item.controller;

import com.skt.Api.GoodsApi;
import com.skt.bo.SpuBo;
import com.skt.pojo.Category;
import com.skt.item.service.CategoryService;
import com.skt.pojo.Sku;
import com.skt.pojo.SpuDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import skt.common.pojo.PageResult;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("category")
public class CategoryController implements GoodsApi {
    @Autowired
    private CategoryService categoryService;

    /**
     * 根据父id查询子节点
     *
     * @param pid
     * @return
     */
    @GetMapping("/list")
    public ResponseEntity<List<Category>> queryCategoriesByPid(@RequestParam(value = "pid", defaultValue = "0") Long pid) {
        if (pid == null || pid < 0) {
            return ResponseEntity.badRequest().build();
        }
        List<Category> categories = categoryService.queryCategoryByPid(pid);
        if (CollectionUtils.isEmpty(categories)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(categories);
    }

    /**
     * 根据品牌Id(bid)查找所属品牌分类
     *
     * @param bid
     * @return
     */
    @GetMapping("bid/{bid}")
    public ResponseEntity<List<Category>> selCateGory(@PathVariable("bid") Long bid,HttpServletRequest request) {
        List<Category> list = this.categoryService.queryByBrandId(bid);
        if (list == null || list.size() < 1) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(list);
    }
    @GetMapping("names")
    public ResponseEntity<List<String>> queryNamesByIds(@RequestParam("ids")List<Long> ids){

        List<String> names = this.categoryService.queryNamesByIds(ids);
        if (CollectionUtils.isEmpty(names)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(names);
    }

    @Override
    public PageResult<SpuBo> querySpuByPage(String key, Boolean saleable, Integer page, Integer rows) {
        return null;
    }

    @Override
    @GetMapping(value = "/spu/detail")
    public SpuDetail querySpuDetailBySpuId(Long id) {
        System.err.println("here");
        return null;
    }

    @Override
    public List<Sku> querySkuBySpuId(Long id) {
        return null;
    }
}
