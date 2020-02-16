package com.hh.demo.service;

import com.hh.demo.common.ServerResponse;
import com.hh.demo.entity.Product;

public interface IProductService {

    ServerResponse addOrUpdate(Product product);

    ServerResponse list(Integer categoryId,
                        String keyword,
                        Integer pageNum,
                        Integer pageSize,
                        String orderBy);

}
