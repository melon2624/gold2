package com.melo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.melo.dto.ProductEntityDTO;
import com.melo.dto.ProductQueryDTO;
import com.melo.entity.Product;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
* @author zhangxin
* @description 针对表【product】的数据库操作Service
* @createDate 2025-03-15 20:55:12
*/
public interface ProductService extends IService<Product> {


    public  void  addProduct(ProductEntityDTO productEntityDTO, MultipartFile[] pictureList);

    public  void  deleteProduct(ProductQueryDTO productQueryDTO);

    public List<ProductEntityDTO> selectProductList(ProductQueryDTO productQueryDTO);


    public ProductEntityDTO selectProductDetail(ProductQueryDTO productQueryDTO);
}
