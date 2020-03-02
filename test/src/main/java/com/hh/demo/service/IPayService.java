package com.hh.demo.service;

import com.hh.demo.common.ServerResponse;
import org.springframework.stereotype.Service;

import java.util.Map;


public interface IPayService {

    ServerResponse pay(Long orderNo);

    String callBackLogic(Map<String,String> signParam);

}
