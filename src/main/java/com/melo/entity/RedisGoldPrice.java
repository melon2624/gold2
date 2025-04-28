package com.melo.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author zhangxin
 * @date 2025-04-27 21:21
 */
@Data
public class RedisGoldPrice {

    @JsonProperty("id")
    private String id;

    @JsonProperty("buy")
    private String buy;

    @JsonProperty("sell")
    private String sell;

    @JsonProperty("dayhigh")
    private String dayhigh;

    @JsonProperty("daylow")
    private String daylow;

    @JsonProperty("closeprice")
    private String closeprice;

    @JsonProperty("WF_WEB_HL_TRADE_DEC")
    private String wfWebHlTradeDec;

    @JsonProperty("sort")
    private String sort;

    @JsonProperty("prod_code")
    private String prodCode;

    @JsonProperty("name")
    private Name name;

    @JsonProperty("mf_id")
    private String mfId;

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBuy() {
        return buy;
    }

    public void setBuy(String buy) {
        this.buy = buy;
    }

    public String getSell() {
        return sell;
    }

    public void setSell(String sell) {
        this.sell = sell;
    }

    public String getDayhigh() {
        return dayhigh;
    }

    public void setDayhigh(String dayhigh) {
        this.dayhigh = dayhigh;
    }

    public String getDaylow() {
        return daylow;
    }

    public void setDaylow(String daylow) {
        this.daylow = daylow;
    }

    public String getCloseprice() {
        return closeprice;
    }

    public void setCloseprice(String closeprice) {
        this.closeprice = closeprice;
    }

    public String getWfWebHlTradeDec() {
        return wfWebHlTradeDec;
    }

    public void setWfWebHlTradeDec(String wfWebHlTradeDec) {
        this.wfWebHlTradeDec = wfWebHlTradeDec;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getProdCode() {
        return prodCode;
    }

    public void setProdCode(String prodCode) {
        this.prodCode = prodCode;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public String getMfId() {
        return mfId;
    }

    public void setMfId(String mfId) {
        this.mfId = mfId;
    }

    // Inner class for "name"
    public static class Name {

        @JsonProperty("zhTW")
        private String zhTW;

        @JsonProperty("zhCN")
        private String zhCN;

        @JsonProperty("enUS")
        private String enUS;

        // Getters and Setters
        public String getZhTW() {
            return zhTW;
        }

        public void setZhTW(String zhTW) {
            this.zhTW = zhTW;
        }

        public String getZhCN() {
            return zhCN;
        }

        public void setZhCN(String zhCN) {
            this.zhCN = zhCN;
        }

        public String getEnUS() {
            return enUS;
        }

        public void setEnUS(String enUS) {
            this.enUS = enUS;
        }
    }


}
