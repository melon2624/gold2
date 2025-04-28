package com.melo.entity;

import lombok.Data;

/**
 * @author zhangxin
 * @date 2025-04-27 20:34
 */
@Data
public class GoldPriceData {

    private int Id;
    private int Type;
    private String TypeName;
    private String Code;
    private String Sname;
    private double Bid;
    private double Ask;
    private double Updown;
    private double UpdnPct;
    private double High;
    private double Low;
    private double Open;
    private double Close;
    private double YdClose;
    private int Order;
    private String Point;


}
