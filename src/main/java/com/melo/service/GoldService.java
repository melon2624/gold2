package com.melo.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.melo.entity.GoldPrice;
import com.melo.entity.GoldPriceData;
import com.melo.entity.RedisGoldPrice;
import com.melo.service.impl.RedisService;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;

/**
 * @author zhangxin
 * @date 2025-04-27 20:32
 */
@Service
public class GoldService {

    @Autowired
    RedisService redisService;

    /*public static void main(String[] args) throws IOException {
        gold();
    }*/

  public   GoldPrice   gold()  {
      // 发送请求
      CloseableHttpResponse response=null;

      try {

          // 定义要发送请求的 URL
          String url = "http://39.108.249.154:8080/index_list";
          // 创建 HttpClient 对象
          CloseableHttpClient httpClient = HttpClients.createDefault();
          // 创建 HttpGet 对象
          HttpGet httpGet = new HttpGet(url);
          // 添加头参数
          httpGet.setHeader("User-Agent", "Mozilla/5.0");
          httpGet.setHeader("Content-Type", "application/json");
          httpGet.setHeader("Origin","http://wap.hxzb9999.com");
          // 发送请求
          response = httpClient.execute(httpGet);

          // 打印响应状态
          //log.info("Response: "+response.getStatusLine());
          System.out.println(new Date() +"Response Code: " + response.getStatusLine());
          // 获取响应实体
          HttpEntity entity = response.getEntity();
          // 打印响应内容
          if (entity != null) {
              //  System.out.println("Response Content: " + EntityUtils.toString(entity));
              String jsonContent = EntityUtils.toString(entity);
              JSONObject jsonObject = JSONObject.parseObject(jsonContent);
              int code = jsonObject.getInteger("code");
              JSONArray data = jsonObject.getJSONArray("data");
              GoldPriceData[] marketDataArray = new GoldPriceData[data.size()];
              // System.out.printf(data.toString());
              if (code==200){
                  for (int i=0;i<data.size();i++){
                      JSONObject goldPriceJson = data.getJSONObject(i);
                      GoldPriceData goldPriceData = new GoldPriceData();
                      goldPriceData.setId(goldPriceJson.getInteger("Id"));
                      goldPriceData.setType(goldPriceJson.getInteger("type"));
                      goldPriceData.setTypeName(goldPriceJson.getString("type_name"));
                      goldPriceData.setCode(goldPriceJson.getString("code"));
                      goldPriceData.setSname(goldPriceJson.getString("sname"));
                      goldPriceData.setBid(goldPriceJson.getDouble("bid"));
                      goldPriceData.setAsk(goldPriceJson.getDouble("ask"));
                      goldPriceData.setUpdown(goldPriceJson.getDouble("updown"));
                      goldPriceData.setUpdnPct(goldPriceJson.getDouble("updn_pct"));
                      goldPriceData.setHigh(goldPriceJson.getDouble("high"));
                      goldPriceData.setLow(goldPriceJson.getDouble("low"));
                      goldPriceData.setOpen(goldPriceJson.getDouble("open"));
                      goldPriceData.setClose(goldPriceJson.getDouble("close"));
                      goldPriceData.setYdClose(goldPriceJson.getDouble("yd_close"));
                      goldPriceData.setOrder(goldPriceJson.getInteger("order"));
                      goldPriceData.setPoint(goldPriceJson.getString("point"));
                      marketDataArray[i] = goldPriceData;
                  }
              }

              GoldPrice goldPriceEntity=new GoldPrice();

              //实时回购金价
              Double goldPrice = Arrays.stream(marketDataArray)
                      .filter(goldPriceData -> goldPriceData.getSname().equals("黄金"))
                      .map(GoldPriceData::getBid)
                      .findFirst().orElse(0.0);
             // System.out.println("实时回购金价"+goldPrice.toString());
              goldPriceEntity.setGoldPrice(String.valueOf(goldPrice));
            LinkedHashMap<String,Object> rtData=  (LinkedHashMap)redisService.getData("gold_price_rt");
            goldPriceEntity.setLondonBuyGoldPrice((String) rtData.get("buy"));
            goldPriceEntity.setLondonSellGoldPrice((String) rtData.get("sell"));

         String usdtRate= redisService.getData("usdt_rate").toString();

         String waterUsdtS=redisService.getData("water_s_usdt").toString();// 散料水位

         String waterUsdtB=redisService.getData("water_b_usdt").toString(); // 板料水位

         String hkdToUsdt=redisService.getData("hkd_to_usdt").toString();// 港币换usdt的价格


            goldPriceEntity.setWaterUsdtB(waterUsdtB);
            goldPriceEntity.setWaterUsdtS(waterUsdtS);

              BigDecimal londonSellGoldPrice =new BigDecimal( goldPriceEntity.getLondonSellGoldPrice());  // 获取伦敦金的卖价

              // 将usdtRate转为BigDecimal
              BigDecimal usdtRateBD = new BigDecimal(usdtRate);

              // 散料usdt水位 处理 waterUsdtS
              BigDecimal waterUsdtSBD = new BigDecimal(waterUsdtS); // 直接转换为BigDecimal
              if (waterUsdtS.startsWith("+") || waterUsdtS.startsWith("-")) {
                  // 处理 + 或 - 符号
                  waterUsdtSBD = new BigDecimal(waterUsdtS);  //  散料水位bigdecimal 计算
              }
              //板料水位
              BigDecimal  waterUsdtBBD =new BigDecimal(waterUsdtB);

              if (waterUsdtB.startsWith("+") || waterUsdtB.startsWith("-")) {
                  // 处理 + 或 - 符号
                  waterUsdtBBD = new BigDecimal(waterUsdtB);  // 板料水位bigdecimal 计算
              }




              //散料 执行计算：goldPriceEntity.getLondonSellGoldPrice() + waterUsdtSBD
              BigDecimal sTotalPrice = londonSellGoldPrice.add(waterUsdtSBD);

              // 计算结果乘以 32.1507
              BigDecimal multiplier = new BigDecimal("32.1507");
              sTotalPrice = sTotalPrice.multiply(multiplier);
              BigDecimal sanUsdt=sTotalPrice.divide(new BigDecimal("1000"), 2, BigDecimal.ROUND_HALF_UP);
                //散料 1g usdt的价格
              goldPriceEntity.setSanUsdt(sanUsdt.toString());

              BigDecimal sanHkd=sanUsdt.multiply(new BigDecimal(hkdToUsdt));
              sanHkd = sanHkd.setScale(2, RoundingMode.HALF_UP);

              //散料港币的价格
              goldPriceEntity.setSanHkd(sanHkd.toString());

              // 然后乘以 usdtRate
              sTotalPrice = sTotalPrice.multiply(usdtRateBD);
              // 最后除以 1000
              BigDecimal result = sTotalPrice.divide(new BigDecimal("1000"), 2, BigDecimal.ROUND_HALF_UP);
              BigDecimal huigou = new BigDecimal(goldPriceEntity.getGoldPrice());  // 水贝回购价格
              BigDecimal finalResult = huigou.subtract(result);
              goldPriceEntity.setJicha(finalResult.toString());



              BigDecimal bTotalPrice = londonSellGoldPrice.add(waterUsdtBBD);
              // 1kg 需要这么u
              bTotalPrice =bTotalPrice.multiply(multiplier);//32.1507

              BigDecimal banUsdt=bTotalPrice.divide(new BigDecimal("1000"), 2, BigDecimal.ROUND_HALF_UP);
              //板料 1g usdt的价格
              goldPriceEntity.setBanUsdt(banUsdt.toString());
              // usdt 1g 单价乘以 u的价格，比如107*7.26=
              bTotalPrice= bTotalPrice.multiply(usdtRateBD);
              // 最后除以 1000 ，人民币的成本价格
               result = bTotalPrice.divide(new BigDecimal("1000"), 2, BigDecimal.ROUND_HALF_UP);

               finalResult = huigou.subtract(result);
               //板料基差
              goldPriceEntity.setJicha2(finalResult.toString());

              return  goldPriceEntity;


          }

      }catch (Exception e){
          e.printStackTrace();
      }finally {
          try {
              response.close();
          }catch (Exception e){
              e.printStackTrace();
          }

      }

      return null;


  }


  public  String getWaterUSDT_S(){

       String water_usdt_s = redisService.getData("water_s_usdt").toString();

       return water_usdt_s;

  }
    public  String getWater_USDT_B(){

        String water_usdt_b = redisService.getData("water_b_usdt").toString();

        return water_usdt_b;

    }

    public String getWater_USD_S(){
      String water_usd_s=redisService.getData("water_s_usd").toString();
      return water_usd_s;
    }

    public String getWater_USD_B(){
        String water_usd_b=redisService.getData("water_b_usd").toString();
        return water_usd_b;
    }


}
