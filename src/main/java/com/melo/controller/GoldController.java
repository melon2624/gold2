package com.melo.controller;


import com.melo.entity.GoldPrice;
import com.melo.service.GoldService;
import com.melo.service.impl.RedisService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

/**
 * @author zhangxin
 * @date 2025-03-02 17:05
 */

@RequestMapping("/gold")
@RestController
@Slf4j
@Tag(name = "黄金数据接口", description = "提供 CRUD 服务")
public class GoldController {


    @Autowired
    RedisService redisService;

    @Autowired
    GoldService goldService;

    @GetMapping("/priceData")
    public Map<String, Object> getPrice(){

        Map<String, Object> result = new HashMap<>();
        List<Map<String, String>> items = new ArrayList<>();

        // 随机生成散料价格（举例）
        double scatterPriceSell = generateRandomNumber();
        double scatterPriceBuy = scatterPriceSell - 15; // 买入价稍微低一点（举例）




        GoldPrice goldPrice= goldService.gold();
        if ( goldPrice!=null){


            // 1. 散料(港币)
            items.add(createItem("散料(港币)", goldPrice.getSanHkd(), "--"));


            // 2. 散料(USDT)
            items.add(createItem("散料(USDT)", goldPrice.getSanUsdt(), "--"));

            // 3. 散料(美金)
            //items.add(createItem("散料(美金)", goldPrice.getSanUsdt(), "--"));

            // 散料水位(USDT)
            items.add(createItem("散料水位(USDT)", goldPrice.getWaterUsdtS(), "--"));

            //板料 1g usdt的价格
            items.add(createItem("整条板料(USDT)", goldPrice.getBanUsdt(), "--"));

            //  整条板料水位(USDT)
            items.add(createItem("整条板料水位(USDT)", goldPrice.getWaterUsdtB(), "--"));



            // 5. 倫敦金 (未扣水)
            items.add(createItem("倫敦金 (未扣水)", goldPrice.getLondonSellGoldPrice(), goldPrice.getLondonBuyGoldPrice()));
            // 6. 中國 水貝
            items.add(createItem("中國 水貝", "-", goldPrice.getGoldPrice()));
            // 7. 散料基差
            items.add(createItem("散料基差", goldPrice.getJicha()+" / 克", "--"));

            // 8. 整条料基差
            items.add(createItem("整条料基差", goldPrice.getJicha2()+" / 克", "--"));
        }




        // 9. --
        items.add(createItem("--", "-", "-"));

        result.put("items", items);

       // goldService.getWater_USDT_B();

        return result;
    }

    private String format(double number) {
        return String.format("%.2f", number);
    }

    // 辅助方法：快速创建每一行数据
    private Map<String, String> createItem(String name, String sell, String buy) {
        Map<String, String> item = new HashMap<>();
        item.put("name", name);
        item.put("sell", sell);
        item.put("buy", buy);
        return item;
    }




    public static double generateRandomNumber() {
        Random random = new Random();
        double number = random.nextDouble() * 1000; // 0 到 1000 的随机小数
        BigDecimal bd = new BigDecimal(number);
        bd = bd.setScale(2, RoundingMode.HALF_UP); // 保留两位小数，四舍五入
        return bd.doubleValue();
    }



    @GetMapping("/get/redisData")
    public Object getData() {
        return redisService.getData("gold_price_rt");
    }

    @RequestMapping("/updateUWaterLever")
    public String updateUWaterLever(@RequestParam("uWaterLever") String uWaterLever){

        if (uWaterLever.startsWith("a") ) {

            uWaterLever = "+" + uWaterLever.substring(1, uWaterLever.length());
        }else {
            uWaterLever = "-" + uWaterLever;
        }
        redisService.setData("water_s_usdt",uWaterLever);

        return "success";
    }

    @RequestMapping("/updateUWaterLever2")
    public String updateUWaterLever2(@RequestParam("uWaterLever") String uWaterLever){
        if (uWaterLever.startsWith("a") ) {

            uWaterLever = "+" + uWaterLever.substring(1, uWaterLever.length());
        }else {
            uWaterLever = "-" + uWaterLever;
        }
        redisService.setData("water_b_usdt",uWaterLever);

        return "success";
    }


    @RequestMapping("/updateUsdtRate")
    public String updateUsdtRate(@RequestParam("u") String u){



        return "success";
    }




}
