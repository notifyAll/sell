package com.imooc.sell.service.impl;

import com.imooc.sell.dao.ProductInfoRepository;
import com.imooc.sell.dto.CartDTO;
import com.imooc.sell.entity.ProductInfo;
import com.imooc.sell.enums.ProductStatusEnum;
import com.imooc.sell.enums.ResultEnum;
import com.imooc.sell.exception.SellException;
import com.imooc.sell.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Resource
    private ProductInfoRepository repository;


    @Override
    public ProductInfo findOne(String productId) {
        return repository.findById(productId).get();
    }

    /**
     * 查询所有在架商品列表
     *
     * @return
     */
    @Override
    public List<ProductInfo> findUpAll() {
        return repository.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public ProductInfo save(ProductInfo productInfo) {
        return repository.save(productInfo);
    }

    /**
     * 加库存
     *
     * @param cartDTOList
     */
    @Override
    @Transactional
    public void increaseStock(List<CartDTO> cartDTOList) {
        for (CartDTO cartDTO:cartDTOList){
            ProductInfo productInfo=repository.findById(cartDTO.getProductId()).get();
            if (productInfo==null){
                throw new  SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            Integer result =productInfo.getProductStock()+cartDTO.getProductQuantity();
            productInfo.setProductStock(result);
            ProductInfo save = repository.save(productInfo);

        }
    }

    /**
     * 减库存
     *
     * @param cartDTOList
     */
    @Override
    @Transactional
    public void decreaseStock(List<CartDTO> cartDTOList) {
        for (CartDTO cartDTO:cartDTOList){
            ProductInfo productInfo = repository.findById(cartDTO.getProductId()).get();
            if (productInfo==null){
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }

            Integer result = productInfo.getProductStock() - cartDTO.getProductQuantity();
            if (result<0){
                throw  new SellException(ResultEnum.PRODUCT_STOCK_ERROR); //库存异常
            }
            productInfo.setProductStock(result);
            repository.save(productInfo);
        }
    }
}
