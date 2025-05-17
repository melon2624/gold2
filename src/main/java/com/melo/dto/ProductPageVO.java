package com.melo.dto;

import lombok.Data;

import java.util.List;

/**
 * @author zhangxin
 * @date 2025-05-10 22:44
 */
@Data
public class ProductPageVO {

    private Integer totalNum;

    List<ProductEntityDTO>  productEntityDTOList;

}
