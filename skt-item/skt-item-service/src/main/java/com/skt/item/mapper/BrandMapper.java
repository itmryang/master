package com.skt.item.mapper;

import com.skt.pojo.Brand;
import org.apache.ibatis.annotations.*;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface BrandMapper extends Mapper<Brand> {
    @Insert("insert into tb_category_brand (category_id,brand_id) values (#{cid},#{bid})")
    int insertCategoryAndBrand(@Param("cid") Long cid,@Param("bid") Long bid);
    @Delete("delete from tb_category_brand where brand_id = #{bid}")
    int deleteCategoryAndBrand(@Param("bid") Long bid);
    @Select("select b.* from tb_brand b inner join tb_category_brand c on b.id = c.brand_id where c.category_id = #{cid}")
    List<Brand> queryBrandByCid(Long cid);
}
