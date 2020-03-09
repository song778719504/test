package com.hh.demo.service;

import com.hh.demo.common.ServerResponse;
import com.hh.demo.entity.pojo.Product;

public interface IProductService {

    ServerResponse addOrUpdate(Product product);

    /**
     * 前台-商品检索
     */
    ServerResponse list(Integer categoryId,
                        String keyword,
                        Integer pageNum,
                        Integer pageSize,
                        String orderBy);


    /**
     * 前台-查看详情
     * @param productId
     * @return
     */
    ServerResponse detail(Integer productId);

    /**
     * 商品减库存
     */
    ServerResponse reduceStock(Integer productId,Integer quantity,int type);

}
