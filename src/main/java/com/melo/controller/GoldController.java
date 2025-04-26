package com.melo.controller;

import com.melo.dto.ProductEntityDTO;
import com.melo.dto.ProductQueryDTO;
import com.melo.restful.RetResponse;
import com.melo.restful.RetResult;
import com.melo.service.ProductService;
import com.melo.util.EmailUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author zhangxin
 * @date 2025-03-02 17:05
 */

@RequestMapping("/gold")
@RestController
@Slf4j
@Tag(name = "产品接口", description = "提供 CRUD 服务")
public class GoldController {

    @Autowired
    ProductService productService;


    @PostMapping("/test")
    public  void  test(){
        System.out.println("qqqqqqqqqqqqqqqqqqqqqqq");
    }

    @Autowired
    EmailUtil emailUtil;

    @PostMapping("/sendEmail")
    public  void sendEmail(){
        emailUtil.sendMessage("1415458002@qq.com","test","test");
    }


    @GetMapping("/selectProduct")
    public  void  selectProduct(@RequestBody ProductQueryDTO productQueryDTO){


        return;
    }


    @PostMapping(value = "/addProduct", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "上传产品信息与图片")
    public RetResult addProduct(
            @Parameter(description = "产品名称") @RequestPart("name") String name,
            @Parameter(description = "产品描述") @RequestPart("desc") String desc,
            @Parameter(description = "产品类型,只填写1或者2或者3") @RequestPart("type") String type,
            @Parameter(description = "图片文件") @RequestPart("picture") MultipartFile[] pictureList) {

        ProductEntityDTO dto = new ProductEntityDTO();
        dto.setName(name);
        dto.setDesc(desc);
        dto.setType(type);

        productService.addProduct(dto, pictureList);
        return RetResponse.OK();
    }

    @PostMapping("/delectProduct")
    public  RetResult  delectProduct(@RequestBody ProductQueryDTO productQueryDTO ){
        productService.deleteProduct(productQueryDTO);
        return RetResponse.OK();
    }

    @GetMapping("/selectProductDetail")
    public  void  selectProductDetail(@RequestBody ProductQueryDTO productQueryDTO){

    }
    @PostMapping("/updateProduct")
    public void  updateProduct(@RequestBody ProductQueryDTO productQueryDTO){
        return;
    }




}
