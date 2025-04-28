package com.melo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

/**
 * @author zhangxin
 * @date 2025-04-27 20:38
 */
@Data
public class GoldPrice {


    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Date createTime;

    //实时回收金价
    private String goldPrice;

    //实时的伦敦金buy价格
    private String londonBuyGoldPrice;

    //实时伦敦金sell价格
    private String londonSellGoldPrice;

    //散料基差
    private String jicha;

    // 板料基差
    private  String   jicha2;

    private String s_usdt_buy;

    private String s_usdt_sell;

    private String sanUsdt;

    private String banUsdt;

    private  String sanHkd;

    private String waterUsdtB;// 板料水位

    private  String waterUsdtS;// 散料水位




}
