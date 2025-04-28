package com.melo.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.melo.dto.ProductEntityDTO;
import com.melo.dto.ProductQueryDTO;
import com.melo.entity.Product;
import com.melo.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

/**
* @author zhangxin
* @description 针对表【product】的数据库操作Service实现
* @createDate 2025-03-15 20:55:12
*/
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product>
    implements ProductService{

    @Autowired
    ProductMapper productMapper;

    public  void  addProduct(ProductEntityDTO productEntityDTO, MultipartFile[] pictureList){

        Product product=new Product();
        product.setDescription(productEntityDTO.getDesc());
        product.setName(productEntityDTO.getName());
        product.setType(productEntityDTO.getType());
        product.setIsEnable(productEntityDTO.getIsEnable());
        product.setCreateTime(new Date());
        product.setUpdateTime(new Date());
        productMapper.insert(product);

        System.out.println("Received name: " + product.getName());

        for (MultipartFile file : pictureList) {
            if (file.isEmpty()) {
                continue;
            }
        }
    }

    @Override
    public void deleteProduct(ProductQueryDTO productQueryDTO) {

        Product product=new Product();

        product.setId(productQueryDTO.getId());
        product.setIsEnable("0");
        product.setUpdateTime(new Date());
        productMapper.updateById(product);

    }

    @Override
    public List<ProductEntityDTO> selectProductList(ProductQueryDTO productQueryDTO) {

        QueryWrapper<Product> queryWrapper=new QueryWrapper<>();

        queryWrapper.eq("zz","a");

        return null;

    }

    //一个主图
    @Override
    public List<ProductEntityDTO> selectProductMobile(ProductQueryDTO productQueryDTO) {

        QueryWrapper<Product> productQueryWrapper = new QueryWrapper<>();

        return null;

    }


    public  List  test(ProductQueryDTO productQueryDTO){

        return null;

    }




}




