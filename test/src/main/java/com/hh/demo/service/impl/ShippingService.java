package com.hh.demo.service.impl;

import com.hh.demo.common.ServerResponse;
import com.hh.demo.common.StatusEnum;
import com.hh.demo.dao.ShippingMapper;
import com.hh.demo.entity.pojo.Shipping;
import com.hh.demo.service.IShippingService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ShippingService implements IShippingService {

    @Resource
    ShippingMapper shippingMapper;

    @Override
    public ServerResponse add(Shipping shipping) {


        int count=shippingMapper.insert(shipping);

        if(count<=0){
            return ServerResponse.serverResponseByFail(
                    StatusEnum.ADDRESS_ADD_FAIL.getStatus(),
                    StatusEnum.ADDRESS_ADD_FAIL.getMsg()
            );
        }


        return ServerResponse.serverResponseBySuccess(null,shipping.getId());// STOPSHIP: 20/2/25 . ;
    }

}
