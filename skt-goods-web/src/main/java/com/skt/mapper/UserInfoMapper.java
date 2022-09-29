package com.skt.mapper;


import com.skt.dto.UserInfo;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoMapper {

    void insertUserInfo(UserInfo userInfo);
}
