package com.melo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Arrays;
import lombok.Data;

/**
 * 
 * @TableName product_file
 */
@TableName(value ="product_file")
@Data
public class ProductFile implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer productId;

    private Integer sequence;

    private String file;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", productId=").append(productId);
        sb.append(", sequence=").append(sequence);
        sb.append(", file=").append(file);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}