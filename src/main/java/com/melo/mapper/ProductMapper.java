package com.melo.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.melo.entity.Product;
import org.apache.ibatis.annotations.Mapper;

/**
* @author zhangxin
* @description 针对表【product】的数据库操作Mapper
* @createDate 2025-03-15 20:55:12
* @Entity generator.domain.Product
*/
@Mapper
public interface ProductMapper extends BaseMapper<Product> {

}




