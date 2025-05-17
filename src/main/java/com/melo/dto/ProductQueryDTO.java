package com.melo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author zhangxin
 * @date 2025-03-15 18:32
 */
@Data
public class ProductQueryDTO {

    @Schema(description = "产品类型,  1 亲子类目，2周岁，3婚爱，4主页轮播图  ", example = "亲子类目")
   private String type;

   private String name;

    private  Integer page=1;

    private Integer pageSize=10;

    private Integer id;

    private   ProductEntityDTO productEntityDTO;

}
