package com.skt.item.service;

import com.skt.pojo.SpecGroup;
import com.skt.pojo.SpecParam;

import java.util.List;

public interface SpecificationService {
    List<SpecGroup> queryGroupByCid(Long cid);
    List<SpecParam> queryParams(Long gid, Long cid, Boolean generic, Boolean searching);
    void insertGroup(SpecGroup specGroup);
    void updateGroupByGid(SpecGroup specGroup);
    void deleteGroupById(Long gid);
}
