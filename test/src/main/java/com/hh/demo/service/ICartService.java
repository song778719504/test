package com.hh.demo.service;

import com.hh.demo.common.ServerResponse;
import com.hh.demo.entity.pojo.Cart;

import java.util.List;

public interface ICartService {

    /**
     * 查看购物车列表
     */
    ServerResponse list(Integer userId);

    /**
     * 购物车中添加商品
     */
    ServerResponse add(Integer userId,Integer productId,Integer count);

    /**
     * 更新购物车某个产品数量
     */
    ServerResponse update(Integer userId,Integer productId,Integer count);

    /**
     * 移除购物车中某个产品
     */
    ServerResponse delete(Integer userId,String productIds);

    /**
     * 查询购物车中已选中的商品
     */
    ServerResponse findCartByUserIdAndChecked(Integer userId);

    /**
     * 批量删除购物车商品
     */
    ServerResponse deleteBatchByIds(List<Cart> cartList);

}
