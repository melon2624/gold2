package com.melo.dto;

import lombok.Data;

/**
 * @author zhangxin
 * @date 2025-03-15 18:32
 */
@Data
public class ProductQueryDTO {

   private String type;

   private String name;

    private  Integer page=1;

    private Integer pageSize=8;

    private Integer id;

    private   ProductEntityDTO productEntityDTO;

}
