package com.melo.dto;

import lombok.Data;

import java.util.List;

/**
 * @author zhangxin
 * @date 2025-03-15 18:39
 */
@Data
public class ProductEntityDTO {

    String name;

    String desc;

    String isEnable;

    String type;

    List[] picture;


}
