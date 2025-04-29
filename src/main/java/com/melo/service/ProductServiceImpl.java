package com.melo.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.github.pagehelper.PageHelper;
import com.melo.dto.ProductEntityDTO;
import com.melo.dto.ProductFileDTO;
import com.melo.dto.ProductQueryDTO;
import com.melo.entity.Product;
import com.melo.entity.ProductFile;
import com.melo.mapper.ProductFileMapper;
import com.melo.mapper.ProductMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
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

    @Autowired
    ProductFileMapper productFileMapper;

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

        List<ProductEntityDTO> productEntityDTOList=new ArrayList<>();

        QueryWrapper<Product> queryWrapper=new QueryWrapper<>();

        if (StringUtils.isNotBlank(productQueryDTO.getType())){
              queryWrapper.eq("type",productQueryDTO.getType());
        }

        if (StringUtils.isNotBlank(productQueryDTO.getName())){
            queryWrapper.like("name",productQueryDTO.getName());
        }

        queryWrapper.eq("is_enable","1");

        PageHelper.startPage(productQueryDTO.getPage(), productQueryDTO.getPageSize());
        List<Product> productList = productMapper.selectList(queryWrapper);

        for (Product product: productList){

            QueryWrapper<ProductFile> productFileQueryWrapper =new QueryWrapper<>();

            productFileQueryWrapper.eq("product_id",product.getId());
            productFileQueryWrapper.eq("sequence",1);

            List<ProductFile> productFileList = productFileMapper.selectList(productFileQueryWrapper);

            ProductEntityDTO productEntityDTO=new ProductEntityDTO();

            productEntityDTO.setProductId(product.getId());
            productEntityDTO.setName(product.getName());
            productEntityDTO.setDesc(product.getDescription());
            productEntityDTO.setType(product.getType());
            if (productFileList.size()>0){
                productEntityDTO.setPrimaryImage(productFileList.get(0).getFile());
            }
            productEntityDTOList.add(productEntityDTO);

        }

        return productEntityDTOList;
    }

    @Override
    public ProductEntityDTO selectProductDetail(ProductQueryDTO productQueryDTO) {

        ProductEntityDTO productEntityDTO=new ProductEntityDTO();

        Product product = productMapper.selectById(productQueryDTO.getId());

        List<ProductFileDTO> productFileDTOList=new ArrayList<>();

        if (product!=null){

            productEntityDTO.setType(product.getType());

            productEntityDTO.setProductId(product.getId());

            productEntityDTO.setName(product.getName());
            productEntityDTO.setDesc(product.getDescription());
            productEntityDTO.setCreateTime(product.getCreateTime());


            QueryWrapper<ProductFile> productFileQueryWrapper=new QueryWrapper<>();
            productFileQueryWrapper.eq("product_id",product.getId());

            List<ProductFile> productFileList = productFileMapper.selectList(productFileQueryWrapper);


            if (productFileList.size()>0){
                for (ProductFile productFile:productFileList){
                    ProductFileDTO productFileDTO=new ProductFileDTO();
                    productFileDTO.setSequence(productFile.getSequence());
                    productFileDTO.setImage(productFile.getFile());
                    productFileDTO.setId(productFile.getId());
                    productFileDTOList.add(productFileDTO);
                }
            }
            productEntityDTO.setIamgeList(productFileDTOList);

        }

        return productEntityDTO;
    }


}




