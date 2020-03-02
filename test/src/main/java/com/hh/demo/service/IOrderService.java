package com.hh.demo.service;

import com.hh.demo.common.ServerResponse;

public interface IOrderService {

    /**
     * 创建订单
     */
    ServerResponse createOrder(Integer userId,Integer shippingId);


    /**
     * 取消订单
     */
    ServerResponse cancelOrder(Long orderNo);

    /**
     * 根据订单号插叙订单信息
     */
    ServerResponse findOrderByOrderNo(Long orderNo);

    /**
     * 支付成功后，修改订单状态
     */
    ServerResponse updateOrderStatus(Long orderNo,String payTime,Integer orderStatus);

}
