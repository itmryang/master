package com.skt.item.mapper;

import com.skt.pojo.Spu;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;

public interface GoodsMapper extends Mapper<Spu> {
    @Update("update tb_spu set saleable = !saleable where id = #{spuId}")
    void updateSaleable(Long spuId);
}
