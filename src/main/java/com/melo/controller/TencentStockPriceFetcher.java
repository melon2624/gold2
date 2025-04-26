package com.melo.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class TencentStockPriceFetcher {

    private static final String SINA_API_URL = "https://hq.sinajs.cn/list=rt_hk00700";

    public static void main(String[] args) {
        try {
            StockData stockData = fetchTencentStockData();
            System.out.println("腾讯控股(00700.HK)实时行情:");
            System.out.println("当前价格: " + stockData.getCurrentPrice());
            System.out.println("今日开盘: " + stockData.getOpenPrice());
            System.out.println("昨日收盘: " + stockData.getPreviousClose());
            System.out.println("今日最高: " + stockData.getHighPrice());
            System.out.println("今日最低: " + stockData.getLowPrice());
            System.out.println("成交量(股): " + stockData.getVolume());
            System.out.println("更新时间: " + stockData.getUpdateTime());
        } catch (Exception e) {
            System.err.println("获取股票数据失败: " + e.getMessage());
        }
    }

    public static StockData fetchTencentStockData() throws Exception {
        URL url = new URL(SINA_API_URL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        // 设置请求头，模拟浏览器访问
        conn.setRequestProperty("Referer", "https://finance.sina.com.cn");
        conn.setRequestProperty("User-Agent", "Mozilla/5.0");

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(conn.getInputStream(), "GBK"))) { // 注意新浪使用GBK编码

            String line = reader.readLine();
            if (line == null) {
                throw new RuntimeException("API返回空数据");
            }

            // 解析返回数据
            return parseSinaResponse(line);
        } finally {
            conn.disconnect();
        }
    }

    private static StockData parseSinaResponse(String response) {
        // 示例响应格式: var hq_str_rt_hk00700="腾讯控股,350.800,352.400,345.600,355.200,345.000,350.800,350.900,14758350,5227623040.000,2023-04-20 16:01:11";

        // 去除多余字符
        String data = response.substring(response.indexOf("\"") + 1, response.lastIndexOf("\""));
        String[] parts = data.split(",");

        if (parts.length < 11) {
            throw new RuntimeException("API返回数据格式异常: " + response);
        }

        StockData stockData = new StockData();
        stockData.setStockName(parts[0]);
        stockData.setCurrentPrice(Double.parseDouble(parts[1]));
        stockData.setPreviousClose(Double.parseDouble(parts[2]));
        stockData.setOpenPrice(Double.parseDouble(parts[3]));
        stockData.setHighPrice(Double.parseDouble(parts[4]));
        stockData.setLowPrice(Double.parseDouble(parts[5]));
        stockData.setBidPrice(Double.parseDouble(parts[6]));
        stockData.setAskPrice(Double.parseDouble(parts[7]));
        stockData.setVolume(Long.parseLong(parts[8]));
        stockData.setTurnover(Double.parseDouble(parts[9]));
        stockData.setUpdateTime(parts[10]);
        return stockData;
    }

    static class StockData {
        private String stockName;
        private double currentPrice;
        private double previousClose;
        private double openPrice;
        private double highPrice;
        private double lowPrice;
        private double bidPrice;
        private double askPrice;
        private long volume;
        private double turnover;
        private String updateTime;

        // getters and setters
        public String getStockName() { return stockName; }
        public void setStockName(String stockName) { this.stockName = stockName; }
        public double getCurrentPrice() { return currentPrice; }
        public void setCurrentPrice(double currentPrice) { this.currentPrice = currentPrice; }
        public double getPreviousClose() { return previousClose; }
        public void setPreviousClose(double previousClose) { this.previousClose = previousClose; }
        public double getOpenPrice() { return openPrice; }
        public void setOpenPrice(double openPrice) { this.openPrice = openPrice; }
        public double getHighPrice() { return highPrice; }
        public void setHighPrice(double highPrice) { this.highPrice = highPrice; }
        public double getLowPrice() { return lowPrice; }
        public void setLowPrice(double lowPrice) { this.lowPrice = lowPrice; }
        public double getBidPrice() { return bidPrice; }
        public void setBidPrice(double bidPrice) { this.bidPrice = bidPrice; }
        public double getAskPrice() { return askPrice; }
        public void setAskPrice(double askPrice) { this.askPrice = askPrice; }
        public long getVolume() { return volume; }
        public void setVolume(long volume) { this.volume = volume; }
        public double getTurnover() { return turnover; }
        public void setTurnover(double turnover) { this.turnover = turnover; }
        public String getUpdateTime() { return updateTime; }
        public void setUpdateTime(String updateTime) { this.updateTime = updateTime; }
    }

}
