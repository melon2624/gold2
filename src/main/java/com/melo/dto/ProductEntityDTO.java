package com.melo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @author zhangxin
 * @date 2025-03-15 18:39
 */
@Data
public class ProductEntityDTO {

    @Schema(description = "产品名称", example = "小米手机")
    private String name;

    @Schema(description = "产品描述", example = "2024新款，超薄设计")
    private String desc;

    @Schema(description = "是否启用（Y/N）", example = "Y")
    private String isEnable;

    @Schema(description = "产品类型", example = "电子产品")
    private String type;


}
