package com.hh.demo;

import com.hh.demo.dao.CartMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class DemoApplicationTests {

    @Resource
    CartMapper cartMapper;

    @Test
    void contextLoads() {

        cartMapper.selectByPrimaryKey(206);

    }

}
