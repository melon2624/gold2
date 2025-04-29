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

import java.util.List;

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


    //
    @GetMapping("/selectProductList")
    @Operation(summary = "移动端和pc端都可以，移动端一次查出来 展示主图和一些描述，点进去查看详情, 传page 和 pageSize,产品类型,用于筛选, name 字段用于模糊搜索，不过可以不做先")
    public List<ProductEntityDTO> selectProductPC(@RequestBody ProductQueryDTO productQueryDTO){
        return  productService.selectProductList(productQueryDTO);
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
    @Operation(summary = "删除产品以及相关的图片文件,主要是传给后端id，通过id删除")
    public  RetResult  delectProduct(@RequestBody ProductQueryDTO productQueryDTO ){
        productService.deleteProduct(productQueryDTO);
        return RetResponse.OK();
    }


    @GetMapping("/selectProductDetail")
    @Operation(summary = "查看产品细节,适用于pc端查看具体某个产品详情和在移动端点进一个产品进入详情页面,传id即可,其他的都不需要")
    public  ProductEntityDTO  selectProductDetail(@RequestBody ProductQueryDTO productQueryDTO){
        ProductEntityDTO productEntityDTO = productService.selectProductDetail(productQueryDTO);
        return productEntityDTO;
    }
    @PostMapping("/updateProduct")
    @Operation(summary = "更新产品,暂时还没实现")
    public void  updateProduct(@RequestBody ProductQueryDTO productQueryDTO){
        return;
    }




}
