package com.skt.item.controller;

import com.skt.pojo.SpecGroup;
import com.skt.pojo.SpecParam;
import com.skt.item.service.Impl.SpecificationServiceImpl;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("spec")
public class SpecificationController {
    @Autowired
    private SpecificationServiceImpl specificationService;

    /**
     * 根据分类id查询分组
     * @param cid
     * @return
     */
    @RequestMapping("/groups/{cid}")
    public ResponseEntity<List<SpecGroup>> queryGruopByCid(@PathVariable("cid") Long cid) {
        List<SpecGroup> specGroups = this.specificationService.queryGroupByCid(cid);
        if(CollectionUtils.isEmpty(specGroups)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(specGroups);
    }

    /**
     * 根据分组gid或cid，或generic，或searching查询具体参数
     * @param gid
     * @return
     */
    @RequestMapping("/params")
    public ResponseEntity<List<SpecParam>> queryParamsByGid(
            @RequestParam(value = "gid" ,required = false) Long gid,
            @RequestParam(value = "cid" ,required = false) Long cid,
            @RequestParam(value = "generic", required = false)Boolean generic,
            @RequestParam(value = "searching", required = false)Boolean searching
            ){
        List<SpecParam> specParams = this.specificationService.queryParams(gid,cid,generic,searching);
        if (CollectionUtils.isEmpty(specParams)){
            ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(specParams);
    }

    /**
     * 添加规格组
     * @param specGroup
     * @return
     */
    @RequestMapping("/group")
    public ResponseEntity<Void> insertGroup(@RequestBody SpecGroup specGroup){
        this.specificationService.insertGroup(specGroup);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 修改规格组名称
     * @param specGroup
     * @return
     */
    @PutMapping("/group")
    public ResponseEntity<Void> updateGroup(@RequestBody SpecGroup specGroup){
        this.specificationService.updateGroupByGid(specGroup);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @RequestMapping("/group/{gid}")
    public ResponseEntity<Void> deleteGroupById(@PathVariable("gid") Long gid){
        this.specificationService.deleteGroupById(gid);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
