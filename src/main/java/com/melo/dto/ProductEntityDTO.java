package com.melo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author zhangxin
 * @date 2025-03-15 18:39
 */
@Data
public class ProductEntityDTO {

    @Schema(description = "产品id", example = "45")
    private Integer productId;

    @Schema(description = "产品名称", example = "宝宝牌")
    private String name;

    @Schema(description = "产品描述", example = "2024新款，适合周岁儿童")
    private String desc;

    @Schema(description = "是否启用（Y/N）", example = "Y")
    private String isEnable;

    @Schema(description = "产品类型", example = "亲子类目")
    private String type;

    @Schema(description = "主图，base64", example = "qwdewfwefwe")
    private String primaryImage;

    @Schema(description = "产品图片集合,base64", example = "swduhsuidwwdwd")
    private  List<ProductFileDTO>  iamgeList;

    private Date createTime;

}
