package com.hh.demo.service;

import com.hh.demo.common.ServerResponse;
import com.hh.demo.entity.pojo.Shipping;

public interface IShippingService {

    ServerResponse add(Shipping shipping);

}
