package com.mapper;

import com.Pojo.commoditySonAttribute;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface commoditySonAttributeMapper {
    //通过商品id查询规格
    List<commoditySonAttribute> findAllCommoditySonAttributeByid(String commodity_id);
}
