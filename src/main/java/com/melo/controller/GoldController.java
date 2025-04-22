package com.melo.controller;

import com.melo.dto.ProductEntityDTO;
import com.melo.dto.ProductQueryDTO;
import com.melo.restful.RetResponse;
import com.melo.restful.RetResult;
import com.melo.service.ProductService;
import com.melo.service.ProductServiceImpl;
import com.melo.util.EmailUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangxin
 * @date 2025-03-02 17:05
 */

@RequestMapping("/gold")
@RestController
@Slf4j
public class GoldController {

    @Autowired
    ProductService productService;


    @RequestMapping("/test")
    public  void  test(){
        System.out.println("qqqqqqqqqqqqqqqqqqqqqqq");
    }

    @Autowired
    EmailUtil emailUtil;

    @RequestMapping("/sendEmail")
    public  void sendEmail(){
        emailUtil.sendMessage("1415458002@qq.com","test","test");
    }


    @RequestMapping("/selectProduct")
    public  void  selectProduct(@RequestBody ProductQueryDTO productQueryDTO){



        return;
    }
    @RequestMapping("/addProduct")
    public RetResult addProduct(@RequestBody ProductEntityDTO productEntityDTO){

        productService.addProduct(productEntityDTO);

        return RetResponse.OK();
    }

    @RequestMapping("/delectProduct")
    public  RetResult  delectProduct(@RequestBody ProductQueryDTO productQueryDTO ){
        productService.deleteProduct(productQueryDTO);
        return RetResponse.OK();
    }

    @RequestMapping("/selectProductDetail")
    public  void  selectProductDetail(@RequestBody ProductQueryDTO productQueryDTO){

    }
    @RequestMapping("/updateProduct")
    public void  updateProduct(@RequestBody ProductQueryDTO productQueryDTO){

        return;

    }




}
