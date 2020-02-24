package com.hh.demo.service;

import com.hh.demo.common.ServerResponse;

public interface IOrderService {

    /**
     * 创建订单
     */
    ServerResponse createOrder(Integer userId,Integer shippingId);

}
