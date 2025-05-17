package com.melo.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.melo.dto.ProductEntityDTO;
import com.melo.dto.ProductFileDTO;
import com.melo.dto.ProductPageVO;
import com.melo.dto.ProductQueryDTO;
import com.melo.entity.Product;
import com.melo.entity.ProductFile;
import com.melo.mapper.ProductFileMapper;
import com.melo.mapper.ProductMapper;
import com.melo.util.FileUtils;
import com.melo.util.TimeUtil;
import lombok.val;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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

    @Value("${upload.pictrueWin}")
    String  uplodaPathWin;

    @Value("${upload.pictrueLinux}")
    String  uplodaPathLinux;

    @Transactional
    public  void  addProduct(ProductEntityDTO productEntityDTO, MultipartFile[] pictureList){

        Product product=new Product();
        product.setDescription(productEntityDTO.getDesc());
        product.setName(productEntityDTO.getName());
        product.setType(productEntityDTO.getType());
        product.setIsEnable("1");
        product.setCreateTime(new Date());
        product.setUpdateTime(new Date());
        productMapper.insert(product);
        String uploadPath = "";

        if (System.getProperty("os.name").toLowerCase().startsWith("win")) {
            uploadPath = uplodaPathWin;
        } else {
            uploadPath = uplodaPathLinux;
        }
        FileUtils.isExist(uploadPath, true);

        System.out.println("Received name: " + product.getName());

        int i=1;
        for (MultipartFile file : pictureList) {
            String originalFilename = file.getOriginalFilename();
            String suffix = "";
            if (originalFilename != null && originalFilename.contains(".")) {
                suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
            }
            // 生成唯一文件名（可选）
            String fileName = TimeUtil.getCurrentTimestamp() + suffix;

            // 最终保存路径
            File destFile = new File(uploadPath, fileName);

            try {
                file.transferTo(destFile);

                ProductFile productFile=new ProductFile();

                productFile.setProductId(product.getId());
                productFile.setFile(fileName);
                productFile.setSequence(i++);

                productFileMapper.insert(productFile);

            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("文件保存失败: " + originalFilename);
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
    public ProductPageVO selectProductList(ProductQueryDTO productQueryDTO) {

        List<ProductEntityDTO> productEntityDTOList=new ArrayList<>();

        QueryWrapper<Product> queryWrapper=new QueryWrapper<>();

        if (StringUtils.isNotBlank(productQueryDTO.getType())){
              queryWrapper.eq("type",productQueryDTO.getType());
        }

        if (StringUtils.isNotBlank(productQueryDTO.getName())){
            queryWrapper.like("name",productQueryDTO.getName());
        }

        queryWrapper.eq("is_enable","1");

        Page startPage = PageHelper.startPage(productQueryDTO.getPage(), productQueryDTO.getPageSize());
        List<Product> productList = productMapper.selectList(queryWrapper);

        for (Product product: productList){

            QueryWrapper<ProductFile> productFileQueryWrapper =new QueryWrapper<>();

            productFileQueryWrapper.eq("product_id",product.getId());
          //  productFileQueryWrapper.eq("sequence",1);

            List<ProductFile> productFileList = productFileMapper.selectList(productFileQueryWrapper);

            List<ProductFileDTO> productFileDTOList=new ArrayList<>();

            ProductEntityDTO productEntityDTO=new ProductEntityDTO();

            productEntityDTO.setProductId(product.getId());
            productEntityDTO.setName(product.getName());
            productEntityDTO.setDesc(product.getDescription());
            productEntityDTO.setType(product.getType());
            if (productFileList.size()>0){
                for (ProductFile productFile:productFileList){

                    if (productFile.getSequence()==1){
                        productEntityDTO.setPrimaryImage("http://101.126.20.67:9000/image/"+productFile.getFile());
                    }

                    ProductFileDTO productFileDTO=new ProductFileDTO();
                    productFileDTO.setSequence(productFile.getSequence());
                    productFileDTO.setImage("http://101.126.20.67:9000/image/"+productFile.getFile());
                    productFileDTO.setId(productFile.getId());
                    productFileDTOList.add(productFileDTO);
                }

                productEntityDTO.setIamgeList(productFileDTOList);
            }
            productEntityDTOList.add(productEntityDTO);

        }
        ProductPageVO productPageVO=new ProductPageVO();

        productPageVO.setProductEntityDTOList(productEntityDTOList);
        productPageVO.setTotalNum((int)startPage.getTotal());
        return productPageVO;
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
            productEntityDTO.setIsEnable(product.getIsEnable());


            QueryWrapper<ProductFile> productFileQueryWrapper=new QueryWrapper<>();
            productFileQueryWrapper.eq("product_id",product.getId());

            List<ProductFile> productFileList = productFileMapper.selectList(productFileQueryWrapper);


            if (productFileList.size()>0){
                for (ProductFile productFile:productFileList){
                    ProductFileDTO productFileDTO=new ProductFileDTO();
                    productFileDTO.setSequence(productFile.getSequence());
                    productFileDTO.setImage("http://101.126.20.67:9000/image/"+productFile.getFile());
                    productFileDTO.setId(productFile.getId());
                    productFileDTOList.add(productFileDTO);
                }
            }
            productEntityDTO.setIamgeList(productFileDTOList);

        }

        return productEntityDTO;
    }


}




