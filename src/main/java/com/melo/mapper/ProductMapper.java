package com.melo.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.melo.dto.ProductEntityDTO;
import com.melo.dto.ProductQueryDTO;
import com.melo.entity.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author zhangxin
* @description 针对表【product】的数据库操作Mapper
* @createDate 2025-03-15 20:55:12
* @Entity generator.domain.Product
*/
@Mapper
public interface ProductMapper extends BaseMapper<Product> {


    public List<ProductEntityDTO>  getProductDataForMobile(@Param("query")ProductQueryDTO productQueryDTO);




}




