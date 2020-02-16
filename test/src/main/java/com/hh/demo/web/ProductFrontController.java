package com.hh.demo.web;

import com.hh.demo.common.Consts;
import com.hh.demo.common.ServerResponse;
import com.hh.demo.common.StatusEnum;
import com.hh.demo.entity.User;
import com.hh.demo.service.IProductService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/product/")
public class ProductFrontController {

    @Resource
    IProductService productService;

    @RequestMapping("list.do")
    public ServerResponse list(
            @RequestParam(required = false,defaultValue = "-1") Integer categoryId,
            @RequestParam(required = false,defaultValue = "") String keyword,
            @RequestParam(required = false,defaultValue = "1") Integer pageNum,
            @RequestParam(required = false,defaultValue = "10") Integer pageSize,
            @RequestParam(required = false,defaultValue = "") String orderBy,
            HttpSession session
    ){
        //登录判断
        User user = (User)session.getAttribute(Consts.USER);
        if (user == null){
            return ServerResponse.serverResponseByFail(
                    StatusEnum.NO_LOGIN.getStatus(),
                    StatusEnum.NO_LOGIN.getMsg()
            );
        }
        return productService.list(categoryId,keyword,pageNum,pageSize,orderBy);
    }

}
