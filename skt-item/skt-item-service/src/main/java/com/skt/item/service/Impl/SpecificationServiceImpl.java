package com.skt.item.service.Impl;

import com.skt.item.mapper.SpecGroupMapper;
import com.skt.item.mapper.SpecParamMapper;
import com.skt.pojo.SpecGroup;
import com.skt.pojo.SpecParam;
import com.skt.item.service.SpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SpecificationServiceImpl implements SpecificationService {
    @Autowired
    private SpecGroupMapper specGroupMapper;
    @Autowired
    private SpecParamMapper specParamMapper;

    @Override
    public List<SpecGroup> queryGroupByCid(Long cid) {
        SpecGroup specGroup = new SpecGroup();
        specGroup.setCid(cid);
        return this.specGroupMapper.select(specGroup);
    }

    @Override
    public List<SpecParam> queryParams(Long gid, Long cid, Boolean generic, Boolean searching) {
        SpecParam specParam = new SpecParam();
        specParam.setGroupId(gid);
        specParam.setCid(cid);
        specParam.setGeneric(generic);
        specParam.setSearching(searching);
        return this.specParamMapper.select(specParam);
    }

    @Override
    @Transactional
    public void insertGroup(SpecGroup specGroup) {
        this.specGroupMapper.insert(specGroup);
    }

    @Override
    @Transactional
    public void updateGroupByGid(SpecGroup specGroup) {
        this.specGroupMapper.updateByPrimaryKeySelective(specGroup);
    }

    @Override
    public void deleteGroupById(Long gid) {
        SpecGroup specGroup = new SpecGroup();
        specGroup.setId(gid);
        this.specGroupMapper.deleteByPrimaryKey(specGroup);
    }
}
